package com.bikcodeh.weatherapp.core.domain.model

/**
 * A place that can be queried for weather data.
 *
 * @property id Identifier assigned by the data source. Used to request the forecast of this exact
 * place, since names alone are ambiguous (e.g. London, UK vs. London, Canada).
 * @property name City or town name.
 * @property region State, province or county. May be empty.
 * @property country Country name. May be empty.
 */
data class Location(
    val id: Long,
    val name: String,
    val region: String,
    val country: String,
)
