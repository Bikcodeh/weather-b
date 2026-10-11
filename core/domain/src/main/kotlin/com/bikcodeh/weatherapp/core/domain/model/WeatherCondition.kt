package com.bikcodeh.weatherapp.core.domain.model

/**
 * Human-readable description of the weather.
 *
 * @property description Localized summary, e.g. "Partly cloudy".
 * @property iconUrl Absolute URL of an icon that represents the condition.
 */
data class WeatherCondition(
    val description: String,
    val iconUrl: String,
)
