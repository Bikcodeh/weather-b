package com.bikcodeh.weatherapp.data.repository

import com.bikcodeh.weatherapp.TestDispatcherProvider
import com.bikcodeh.weatherapp.data.remote.api.WeatherApi
import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
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

    // --------------------------------
    // getSearch
    // --------------------------------

    @Test
    fun `getSearch returns success when api response is successful`() = runTest {
        // GIVEN
        val query = "Bogota"
        val locations = listOf(
            LocationDto(
                name = "Bogotá",
                country = "Colombia",
                region = "Cundinamarca",
                id = 123
            )
        )

        val response = Response.success(locations)

        coEvery {
            weatherApi.searchLocation(query)
        } returns response

        // WHEN
        val result = repository.getSearch(query)

        // THEN
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(locations)

        coVerify(exactly = 1) {
            weatherApi.searchLocation(query)
        }
    }


    @Test
    fun `getSearch returns failure when api throws exception`() = runTest {
        // GIVEN
        val query = "Bogota"
        val exception = RuntimeException("Network error")

        coEvery { weatherApi.searchLocation(query) } throws exception

        // WHEN
        val result = repository.getSearch(query)

        // THEN
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)

        coVerify(exactly = 1) { weatherApi.searchLocation(query) }
    }

    // --------------------------------
    // getForecast
    // --------------------------------

    @Test
    fun `getForecast returns success when api call succeeds`() = runTest {
        // GIVEN
        val query = "Bogota"
        val days = "2"

        val forecast = ForecastResponseDto(
            location = mockk(),
            current = mockk(),
            forecast = mockk()
        )

        coEvery {
            weatherApi.getCurrentConditions(query, days)
        } returns Response.success(forecast)

        // WHEN
        val result = repository.getForecast(query, days)

        // THEN
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(forecast)

        coVerify(exactly = 1) {
            weatherApi.getCurrentConditions(query, days)
        }
    }

    @Test
    fun `getForecast returns failure when api throws exception`() = runTest {
        // GIVEN
        val query = "Bogota"
        val days = "2"
        val exception = IllegalStateException("API error")

        coEvery {
            weatherApi.getCurrentConditions(query, days)
        } throws exception

        // WHEN
        val result = repository.getForecast(query, days)

        // THEN
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)

        coVerify(exactly = 1) {
            weatherApi.getCurrentConditions(query, days)
        }
    }
}
