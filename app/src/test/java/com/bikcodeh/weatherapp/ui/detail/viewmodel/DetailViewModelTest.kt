package com.bikcodeh.weatherapp.ui.detail.viewmodel

import app.cash.turbine.test
import com.bikcodeh.weatherapp.CoroutineRule
import com.bikcodeh.weatherapp.TestDispatcherProvider
import com.bikcodeh.weatherapp.data.remote.dto.CurrentWeatherDto
import com.bikcodeh.weatherapp.data.remote.dto.DayDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastDayDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.data.remote.dto.LocationForecastDto
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()
    private val weatherRepository: WeatherRepository = mockk()
    private val testDispatcher = TestDispatcherProvider(UnconfinedTestDispatcher())
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        viewModel = DetailViewModel(
            weatherRepository = weatherRepository,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `initial state is loading`() {
        val state = viewModel.state.value

        assertThat(state.isLoading).isTrue()
        assertThat(state.current).isNull()
        assertThat(state.forecast).isEmpty()
    }

    @Test
    fun `load weather updates state on success`() = runTest {
        // GIVEN
        val forecast = ForecastResponseDto(
            location = LocationForecastDto(
                country = "Palestinian Territories",
                name = "Carter Hickman",
                region = "vituperata"
            ),
            current = CurrentWeatherDto(
                tempC = 10.11,
                feelslikeC = 12.13,
                humidity = 3047,
                windKph = 14.15,
                windDir = "delenit",
                precipMm = 16.17,
                visKm = 18.19,
                condition = null
            ),
            forecast = ForecastDto(
                forecastday = listOf(
                    ForecastDayDto(
                        date = "intellegat",
                        day = DayDto(maxtempC = 24.25, mintempC = 26.27, condition = null)
                    )
                )
            )
        )
        val result = Result.success(forecast)

        coEvery {
            weatherRepository.getForecast("Bogota", "2")
        } returns result

        // WHEN
        viewModel.sendEvent(DetailEvent.LoadWeather("Bogota"))
        advanceUntilIdle()

        // THEN
        val state = viewModel.state.value

        assertThat(state.current).isNotNull()
        assertThat(state.forecast).isNotEmpty()
    }

    @Test
    fun `load weather emits error effect on failure`() = runTest {
        // GIVEN
        coEvery {
            weatherRepository.getForecast("Bogota", "2")
        } returns Result.failure(IOException("No internet"))

        // WHEN / THEN
        viewModel.effects.test {
            viewModel.sendEvent(DetailEvent.LoadWeather("Bogota"))
            advanceUntilIdle()

            val effect = awaitItem()

            assertThat(effect).isInstanceOf(DetailEffect.ShowErrorMessage::class.java)
        }
    }

    @Test
    fun `on back clicked emits navigate back effect`() = runTest {
        viewModel.effects.test {
            // WHEN
            viewModel.sendEvent(DetailEvent.OnBackClicked)

            // THEN
            val effect = awaitItem()

            assertThat(effect).isEqualTo(DetailEffect.NavigateBack)
        }
    }


}