package com.bikcodeh.weatherapp.ui.model

data class CurrentWeatherUiModel(
    val location: String,
    val temperatureCelsius: Double?,
    val feelsLikeCelsius: Double,
    val humidityPercentage: Long,
    val windSpeedKph: Double,
    val windDirection: String,
    val precipitationMm: Double,
    val visibilityKm: Long,
    val weatherConditionText: String,
    val weatherConditionIconUrl: String
)