package com.bikcodeh.weatherapp.core.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response of `forecast.json`. Only the fields used by the app are declared; unknown fields are
 * ignored by the JSON configuration. Essential values are non-nullable on purpose: a missing
 * temperature must surface as a parsing error instead of being displayed as a fake `0°`.
 */
@Serializable
internal data class ForecastResponseDto(
    val location: ForecastLocationDto,
    val current: CurrentWeatherDto,
    val forecast: ForecastDto,
)

@Serializable
internal data class ForecastLocationDto(
    val name: String,
    val region: String = "",
    val country: String = "",
)

@Serializable
internal data class CurrentWeatherDto(
    @SerialName("temp_c") val temperatureCelsius: Double,
    @SerialName("feelslike_c") val feelsLikeCelsius: Double,
    val humidity: Int,
    @SerialName("wind_kph") val windKph: Double,
    @SerialName("precip_mm") val precipitationMm: Double,
    val condition: ConditionDto,
)

@Serializable
internal data class ForecastDto(
    @SerialName("forecastday") val forecastDays: List<ForecastDayDto>,
)

@Serializable
internal data class ForecastDayDto(
    /** ISO-8601 date (`yyyy-MM-dd`) in the location's time zone. */
    val date: String,
    val day: DayDto,
)

@Serializable
internal data class DayDto(
    @SerialName("maxtemp_c") val maxTemperatureCelsius: Double,
    @SerialName("mintemp_c") val minTemperatureCelsius: Double,
    val condition: ConditionDto,
)

@Serializable
internal data class ConditionDto(
    val text: String,
    /** Protocol-relative URL, e.g. `//cdn.weatherapi.com/weather/64x64/day/113.png`. */
    val icon: String,
)
