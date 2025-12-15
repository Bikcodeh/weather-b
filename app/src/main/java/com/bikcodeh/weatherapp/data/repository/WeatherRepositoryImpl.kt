package com.bikcodeh.weatherapp.data.repository

import com.bikcodeh.weatherapp.data.remote.api.WeatherApi
import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
import com.bikcodeh.weatherapp.domain.commons.DispatcherProvider
import com.bikcodeh.weatherapp.data.remote.network.makeSafeRequest
import com.bikcodeh.weatherapp.domain.repository.WeatherRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    val weatherApi: WeatherApi,
    private val dispatcherProvider: DispatcherProvider
) : WeatherRepository {

    override suspend fun getSearch(query: String): Result<List<LocationDto>> =
        withContext(dispatcherProvider.io) {
            makeSafeRequest {
                weatherApi.searchLocation(query)
            }
        }

    override suspend fun getForecast(
        query: String,
        days: String
    ): Result<ForecastResponseDto> =
        withContext(dispatcherProvider.io) {
            makeSafeRequest {
                weatherApi.getCurrentConditions(
                    query,
                    days
                )
            }
        }
}
