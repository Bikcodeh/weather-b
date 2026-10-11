package com.bikcodeh.weatherapp.core.data.repository

import com.bikcodeh.weatherapp.core.data.di.IoDispatcher
import com.bikcodeh.weatherapp.core.data.error.safeApiCall
import com.bikcodeh.weatherapp.core.data.mapper.toDomain
import com.bikcodeh.weatherapp.core.data.remote.WeatherApi
import com.bikcodeh.weatherapp.core.data.remote.dto.LocationDto
import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.core.domain.repository.WeatherRepository
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * [WeatherRepository] backed by the WeatherAPI.com REST API.
 *
 * Main-safe: requests and DTO mapping run on [ioDispatcher]. Mapping happens inside [safeApiCall]
 * so malformed data becomes a [com.bikcodeh.weatherapp.core.domain.error.DomainError.Parsing]
 * instead of a crash.
 */
internal class DefaultWeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : WeatherRepository {

    override suspend fun searchLocations(query: String): DomainResult<List<Location>> =
        withContext(ioDispatcher) {
            safeApiCall { weatherApi.searchLocations(query).map(LocationDto::toDomain) }
        }

    override suspend fun getForecast(locationId: Long, days: Int): DomainResult<WeatherForecast> =
        withContext(ioDispatcher) {
            safeApiCall {
                weatherApi
                    .getForecast(query = locationQuery(locationId), days = days)
                    .toDomain(locationId)
            }
        }

    /** WeatherAPI selects an exact location (from the search endpoint) with `id:<id>`. */
    private fun locationQuery(locationId: Long): String = "id:$locationId"
}
