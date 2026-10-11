package com.bikcodeh.weatherapp.core.domain.model

/**
 * Weather conditions at the time of the request.
 *
 * @property temperatureCelsius Air temperature in °C.
 * @property feelsLikeCelsius Apparent temperature in °C.
 * @property humidityPercent Relative humidity, from 0 to 100.
 * @property windSpeedKph Wind speed in km/h.
 * @property precipitationMm Precipitation amount in millimeters.
 * @property condition Textual and visual description of the weather.
 */
data class CurrentWeather(
    val temperatureCelsius: Double,
    val feelsLikeCelsius: Double,
    val humidityPercent: Int,
    val windSpeedKph: Double,
    val precipitationMm: Double,
    val condition: WeatherCondition,
)
