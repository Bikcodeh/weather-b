package com.bikcodeh.weatherapp.data.repository

import com.bikcodeh.weatherapp.data.mapper.toDomain
import com.bikcodeh.weatherapp.data.remote.api.WeatherApi
import com.bikcodeh.weatherapp.data.remote.network.makeSafeRequest
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import com.bikcodeh.weatherapp.domain.model.Location
import com.bikcodeh.weatherapp.domain.model.WeatherForecast
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dispatcherProvider: DispatcherProvider
) : WeatherRepository {

    override suspend fun getSearch(query: String): Result<List<Location>> =
        withContext(dispatcherProvider.io) {
            makeSafeRequest {
                weatherApi.searchLocation(query)
            }.map { listDto ->
                listDto.map { it.toDomain() }
            }
        }

    override suspend fun getForecast(
        query: String,
        days: String
    ): Result<WeatherForecast> =
        withContext(dispatcherProvider.io) {
            makeSafeRequest {
                weatherApi.getCurrentConditions(
                    query,
                    days
                )
            }.map { it.toDomain() }
        }
}
