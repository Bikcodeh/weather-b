package com.bikcodeh.weatherapp.data.repository

import com.bikcodeh.weatherapp.TestDispatcherProvider
import com.bikcodeh.weatherapp.data.remote.api.WeatherApi
import com.bikcodeh.weatherapp.data.remote.dto.*
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var weatherApi: WeatherApi
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: WeatherRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        weatherApi = mockk()
        dispatcherProvider = TestDispatcherProvider(testDispatcher)

        repository = WeatherRepositoryImpl(
            weatherApi = weatherApi,
            dispatcherProvider = dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSearch returns success with domain models when api response is successful`() = runTest {
        // GIVEN
        val query = "Bogota"
        val locationsDto = listOf(
            LocationDto(
                name = "Bogotá",
                country = "Colombia",
                region = "Cundinamarca",
                id = 123
            )
        )

        val response = Response.success(locationsDto)

        coEvery {
            weatherApi.searchLocation(query)
        } returns response

        // WHEN
        val result = repository.getSearch(query)

        // THEN
        assertThat(result.isSuccess).isTrue()
        val domainList = result.getOrNull()
        assertThat(domainList).isNotNull
        assertThat(domainList!![0].name).isEqualTo("Bogotá")
        assertThat(domainList[0].id).isEqualTo(123)

        coVerify(exactly = 1) {
            weatherApi.searchLocation(query)
        }
    }

    @Test
    fun `getForecast returns success with domain model when api call succeeds`() = runTest {
        // GIVEN
        val query = "Bogota"
        val days = "2"

        val forecastDto = ForecastResponseDto(
            location = LocationForecastDto("Colombia", "Bogota", "Cundinamarca"),
            current = CurrentWeatherDto(
                tempC = 15.0,
                feelslikeC = 14.0,
                humidity = 60L,
                windKph = 10.0,
                windDir = "N",
                precipMm = 0.0,
                visKm = 10.0,
                condition = ConditionDto("Sunny", "icon")
            ),
            forecast = ForecastDto(
                forecastday = listOf(
                    ForecastDayDto(
                        date = "2023-10-27",
                        day = DayDto(20.0, 10.0, ConditionDto("Cloudy", "icon_day"))
                    )
                )
            )
        )

        coEvery {
            weatherApi.getCurrentConditions(query, days)
        } returns Response.success(forecastDto)

        // WHEN
        val result = repository.getForecast(query, days)

        // THEN
        assertThat(result.isSuccess).isTrue()
        val domainResult = result.getOrNull()
        assertThat(domainResult).isNotNull
        assertThat(domainResult!!.location.name).isEqualTo("Bogota")
        assertThat(domainResult.current.tempC).isEqualTo(15.0)
        assertThat(domainResult.forecast).hasSize(1)
        assertThat(domainResult.forecast[0].date).isEqualTo("2023-10-27")

        coVerify(exactly = 1) {
            weatherApi.getCurrentConditions(query, days)
        }
    }
}
