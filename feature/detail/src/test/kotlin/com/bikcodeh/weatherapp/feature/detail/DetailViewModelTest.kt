package com.bikcodeh.weatherapp.feature.detail

import app.cash.turbine.test
import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import com.bikcodeh.weatherapp.core.domain.usecase.GetWeatherForecastUseCase
import com.bikcodeh.weatherapp.core.testing.MainDispatcherRule
import com.bikcodeh.weatherapp.core.testing.data.TestData
import com.bikcodeh.weatherapp.core.testing.repository.FakeWeatherRepository
import com.bikcodeh.weatherapp.core.testing.repository.FakeWeatherRepository.ForecastRequest
import com.bikcodeh.weatherapp.core.ui.error.messageRes
import com.bikcodeh.weatherapp.feature.detail.model.toUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeWeatherRepository().apply { responseDelayMillis = 100 }
    private lateinit var viewModel: DetailViewModel

    // Created after MainDispatcherRule installs the test dispatcher: viewModelScope reads
    // Dispatchers.Main when the ViewModel is constructed.
    @Before
    fun setUp() {
        viewModel = DetailViewModel(
            locationId = TestData.bogota.id,
            getWeatherForecast = GetWeatherForecastUseCase(repository),
        )
    }

    @Test
    fun `nothing is loaded until the UI subscribes`() = runTest {
        advanceUntilIdle()

        assertThat(repository.forecastRequests).isEmpty()
    }

    @Test
    fun `loads the forecast of the location`() = runTest {
        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(DetailUiState.Loading)
            val expected = DetailUiState.Success(TestData.bogotaForecast.toUiModel())
            assertThat(awaitItem()).isEqualTo(expected)
        }
        assertThat(repository.forecastRequests).containsExactly(
            ForecastRequest(TestData.bogota.id, GetWeatherForecastUseCase.DEFAULT_FORECAST_DAYS),
        )
    }

    @Test
    fun `failures emit an error with the matching message`() = runTest {
        repository.forecastResult = DomainResult.Failure(DomainError.NotFound)

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(DetailUiState.Loading)
            val expected = DetailUiState.Error(DomainError.NotFound.messageRes())
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `retry loads the forecast again`() = runTest {
        repository.forecastResult = DomainResult.Failure(DomainError.Network)

        viewModel.uiState.test {
            skipItems(2) // Loading, Error

            repository.forecastResult = DomainResult.Success(TestData.bogotaForecast)
            viewModel.onRetry()

            assertThat(awaitItem()).isEqualTo(DetailUiState.Loading)
            assertThat(awaitItem()).isInstanceOf(DetailUiState.Success::class.java)
        }
        assertThat(repository.forecastRequests).hasSize(2)
    }
}
