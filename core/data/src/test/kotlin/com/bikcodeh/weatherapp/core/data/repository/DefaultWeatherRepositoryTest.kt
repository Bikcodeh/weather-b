package com.bikcodeh.weatherapp.core.data.repository

import com.bikcodeh.weatherapp.core.data.WeatherApiConfig
import com.bikcodeh.weatherapp.core.data.di.NetworkModule
import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import com.bikcodeh.weatherapp.core.testing.data.TestData
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Integration test of the whole remote stack (Retrofit + kotlinx.serialization + interceptors +
 * error mapping) against a local [MockWebServer] serving real WeatherAPI payloads.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DefaultWeatherRepositoryTest {

    private val server = MockWebServer()

    @Before
    fun setUp() = server.start()

    @After
    fun tearDown() = server.close()

    @Test
    fun `searchLocations maps every location returned by the API`() = runTest {
        server.enqueue(MockResponse(body = readFixture("search_bogota.json")))

        val result = repository().searchLocations("Bogota")

        assertThat(result).isEqualTo(
            DomainResult.Success(
                listOf(
                    TestData.bogota,
                    Location(
                        id = 2_617_582,
                        name = "Bogota",
                        region = "New Jersey",
                        country = "United States of America",
                    ),
                ),
            ),
        )
    }

    @Test
    fun `searchLocations sends the query and authenticates with the api key`() = runTest {
        server.enqueue(MockResponse(body = "[]"))

        repository().searchLocations("Bogota")

        val requestUrl = server.takeRequest().url
        assertThat(requestUrl.encodedPath).isEqualTo("/v1/search.json")
        assertThat(requestUrl.queryParameter("q")).isEqualTo("Bogota")
        assertThat(requestUrl.queryParameter("key")).isEqualTo(API_KEY)
    }

    @Test
    fun `searchLocations returns an empty list when nothing matches`() = runTest {
        server.enqueue(MockResponse(body = "[]"))

        val result = repository().searchLocations("Xyzzy")

        assertThat(result).isEqualTo(DomainResult.Success(emptyList<Location>()))
    }

    @Test
    fun `getForecast maps the response to the domain model`() = runTest {
        server.enqueue(MockResponse(body = readFixture("forecast_bogota.json")))

        val result = repository().getForecast(locationId = TestData.bogota.id, days = 3)

        assertThat(result).isEqualTo(DomainResult.Success(TestData.bogotaForecast))
    }

    @Test
    fun `getForecast requests the exact location by id`() = runTest {
        server.enqueue(MockResponse(body = readFixture("forecast_bogota.json")))

        repository().getForecast(locationId = 2_805_431, days = 3)

        val requestUrl = server.takeRequest().url
        assertThat(requestUrl.encodedPath).isEqualTo("/v1/forecast.json")
        assertThat(requestUrl.queryParameter("q")).isEqualTo("id:2805431")
        assertThat(requestUrl.queryParameter("days")).isEqualTo("3")
    }

    @Test
    fun `HTTP error statuses are mapped to domain errors`() = runTest {
        val expectedErrors = mapOf(
            400 to DomainError.NotFound,
            401 to DomainError.Unauthorized,
            403 to DomainError.Unauthorized,
            404 to DomainError.NotFound,
            429 to DomainError.Unknown,
            500 to DomainError.Server,
            503 to DomainError.Server,
        )
        val repository = repository()

        expectedErrors.forEach { (status, expectedError) ->
            server.enqueue(MockResponse(code = status, body = ERROR_BODY))

            val result = repository.searchLocations("Bogota")

            val expected = DomainResult.Failure(expectedError)
            assertThat(result).describedAs("HTTP $status").isEqualTo(expected)
        }
    }

    @Test
    fun `malformed JSON is mapped to a parsing error`() = runTest {
        server.enqueue(MockResponse(body = """{"location": {"name": "Bogota"}}"""))

        val result = repository().getForecast(locationId = 1, days = 3)

        assertThat(result).isEqualTo(DomainResult.Failure(DomainError.Parsing))
    }

    @Test
    fun `malformed dates are mapped to a parsing error`() = runTest {
        server.enqueue(MockResponse(body = readFixture("forecast_invalid_date.json")))

        val result = repository().getForecast(locationId = 1, days = 3)

        assertThat(result).isEqualTo(DomainResult.Failure(DomainError.Parsing))
    }

    @Test
    fun `an unreachable server is mapped to a network error`() = runTest {
        val repository = repository()
        server.close()

        val result = repository.searchLocations("Bogota")

        assertThat(result).isEqualTo(DomainResult.Failure(DomainError.Network))
    }

    @Test
    fun `a slow server is mapped to a timeout error`() = runTest {
        server.enqueue(
            MockResponse.Builder()
                .body("[]")
                .headersDelay(1, TimeUnit.SECONDS)
                .build(),
        )

        val result = repository(readTimeoutMillis = 100).searchLocations("Bogota")

        assertThat(result).isEqualTo(DomainResult.Failure(DomainError.Timeout))
    }

    /** Builds the repository with the production Retrofit/OkHttp setup pointed at [server]. */
    private fun TestScope.repository(readTimeoutMillis: Long = 5_000): DefaultWeatherRepository {
        val config = WeatherApiConfig(
            apiKey = API_KEY,
            enableHttpLogging = false,
            baseUrl = server.url("/v1/").toString(),
        )
        val client = NetworkModule.provideOkHttpClient(config).newBuilder()
            .readTimeout(readTimeoutMillis, TimeUnit.MILLISECONDS)
            .build()
        val api = NetworkModule.provideWeatherApi({ client }, NetworkModule.provideJson(), config)
        return DefaultWeatherRepository(api, UnconfinedTestDispatcher(testScheduler))
    }

    private fun readFixture(name: String): String {
        val fixture = checkNotNull(javaClass.classLoader?.getResource(name)) { "Missing $name" }
        return fixture.readText()
    }

    private companion object {
        const val API_KEY = "test-api-key"
        const val ERROR_BODY = """{"error":{"code":1006,"message":"No matching location found."}}"""
    }
}
