package com.bikcodeh.weatherapp.core.domain.usecase

import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.core.domain.repository.WeatherRepository
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import javax.inject.Inject

/** Gets the current weather and the daily forecast of a location. */
class GetWeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {

    /**
     * @param locationId Identifier of a [com.bikcodeh.weatherapp.core.domain.model.Location].
     * @param days Number of forecast days, today included. Must be positive.
     */
    suspend operator fun invoke(
        locationId: Long,
        days: Int = DEFAULT_FORECAST_DAYS,
    ): DomainResult<WeatherForecast> {
        require(days > 0) { "days must be positive but was $days" }
        return weatherRepository.getForecast(locationId, days)
    }

    companion object {
        /** Today plus the next two days. */
        const val DEFAULT_FORECAST_DAYS = 3
    }
}
