package com.bikcodeh.weatherapp.core.domain.model

/**
 * Current weather plus the daily forecast of a [location].
 *
 * @property days Daily forecast in chronological order. The first item is today in the
 * location's time zone.
 */
data class WeatherForecast(
    val location: Location,
    val current: CurrentWeather,
    val days: List<ForecastDay>,
)
