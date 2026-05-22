package com.bikcodeh.weatherapp.ui.detail.viewmodel

import app.cash.turbine.test
import com.bikcodeh.weatherapp.CoroutineRule
import com.bikcodeh.weatherapp.TestDispatcherProvider
import com.bikcodeh.weatherapp.domain.model.Condition
import com.bikcodeh.weatherapp.domain.model.CurrentWeather
import com.bikcodeh.weatherapp.domain.model.ForecastDay
import com.bikcodeh.weatherapp.domain.model.Location
import com.bikcodeh.weatherapp.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.domain.usecase.GetWeatherForecastUseCase
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
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase = mockk()
    private val testDispatcher = TestDispatcherProvider(UnconfinedTestDispatcher())
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        viewModel = DetailViewModel(
            getWeatherForecastUseCase = getWeatherForecastUseCase,
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
        val forecast = WeatherForecast(
            location = Location(
                country = "Palestinian Territories",
                name = "Carter Hickman",
                region = "vituperata"
            ),
            current = CurrentWeather(
                tempC = 10.11,
                feelslikeC = 12.13,
                humidity = 3047,
                windKph = 14.15,
                windDir = "delenit",
                precipMm = 16.17,
                visKm = 18.19,
                condition = Condition("Clear", "icon")
            ),
            forecast = listOf(
                ForecastDay(
                    date = "intellegat",
                    maxTempC = 24.25,
                    minTempC = 26.27,
                    condition = Condition("Sunny", "icon")
                )
            )
        )
        val result = Result.success(forecast)

        coEvery {
            getWeatherForecastUseCase("Bogota")
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
            getWeatherForecastUseCase("Bogota")
        } returns Result.failure(IOException("No internet"))

        // WHEN / THEN
        viewModel.effects.test {
            viewModel.sendEvent(DetailEvent.LoadWeather("Bogota"))
            advanceUntilIdle()

            val effect = awaitItem()

            assertThat(effect).isInstanceOf(DetailEffect.ShowErrorMessage::class.java)
        }
    }
}
