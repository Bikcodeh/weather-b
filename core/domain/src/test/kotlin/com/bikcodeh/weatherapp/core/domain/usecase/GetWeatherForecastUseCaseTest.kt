package com.bikcodeh.weatherapp.core.domain.usecase

import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import com.bikcodeh.weatherapp.core.testing.data.TestData
import com.bikcodeh.weatherapp.core.testing.repository.FakeWeatherRepository
import com.bikcodeh.weatherapp.core.testing.repository.FakeWeatherRepository.ForecastRequest
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GetWeatherForecastUseCaseTest {

    private val repository = FakeWeatherRepository()
    private val getWeatherForecast = GetWeatherForecastUseCase(repository)

    @Test
    fun `requests the default number of days for the given location`() = runTest {
        getWeatherForecast(locationId = TestData.bogota.id)

        assertThat(repository.forecastRequests).containsExactly(
            ForecastRequest(TestData.bogota.id, GetWeatherForecastUseCase.DEFAULT_FORECAST_DAYS),
        )
    }

    @Test
    fun `returns the repository result`() = runTest {
        repository.forecastResult = DomainResult.Failure(DomainError.NotFound)

        val result = getWeatherForecast(locationId = 42, days = 1)

        assertThat(result).isEqualTo(DomainResult.Failure(DomainError.NotFound))
    }

    @Test
    fun `rejects a non positive number of days`() = runTest {
        val error = runCatching { getWeatherForecast(locationId = 42, days = 0) }.exceptionOrNull()

        assertThat(error).isInstanceOf(IllegalArgumentException::class.java)
        assertThat(repository.forecastRequests).isEmpty()
    }
}
