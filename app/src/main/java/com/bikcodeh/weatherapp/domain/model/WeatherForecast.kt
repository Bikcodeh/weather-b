package com.bikcodeh.weatherapp.domain.model

data class WeatherForecast(
    val location: Location,
    val current: CurrentWeather,
    val forecast: List<ForecastDay>
)

data class CurrentWeather(
    val tempC: Double,
    val feelslikeC: Double,
    val humidity: Long,
    val windKph: Double,
    val windDir: String,
    val precipMm: Double,
    val visKm: Double,
    val condition: Condition
)

data class ForecastDay(
    val date: String,
    val maxTempC: Double,
    val minTempC: Double,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)
