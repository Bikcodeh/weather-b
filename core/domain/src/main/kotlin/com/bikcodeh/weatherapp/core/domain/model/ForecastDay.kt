package com.bikcodeh.weatherapp.core.domain.model

import java.time.LocalDate

/**
 * Forecast summary for a single day.
 *
 * @property date Calendar date in the location's time zone.
 * @property maxTemperatureCelsius Highest expected temperature in °C.
 * @property minTemperatureCelsius Lowest expected temperature in °C.
 * @property condition Expected weather for the day.
 */
data class ForecastDay(
    val date: LocalDate,
    val maxTemperatureCelsius: Double,
    val minTemperatureCelsius: Double,
    val condition: WeatherCondition,
)
