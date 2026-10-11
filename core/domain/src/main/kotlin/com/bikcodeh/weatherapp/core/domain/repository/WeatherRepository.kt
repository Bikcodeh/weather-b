package com.bikcodeh.weatherapp.core.domain.repository

import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.core.domain.result.DomainResult

/**
 * Entry point to weather data. Implementations live in the data layer and must be main-safe:
 * callers can invoke these functions from the main thread.
 */
interface WeatherRepository {

    /** Returns the locations whose name matches [query]; an empty list when nothing matches. */
    suspend fun searchLocations(query: String): DomainResult<List<Location>>

    /** Returns the current weather and a forecast of [days] days for the location [locationId]. */
    suspend fun getForecast(locationId: Long, days: Int): DomainResult<WeatherForecast>
}
