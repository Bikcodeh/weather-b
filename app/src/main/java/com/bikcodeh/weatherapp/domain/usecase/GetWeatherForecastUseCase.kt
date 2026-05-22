package com.bikcodeh.weatherapp.domain.usecase

import com.bikcodeh.weatherapp.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(query: String, days: String = "3"): Result<WeatherForecast> {
        return weatherRepository.getForecast(query, days)
    }
}
