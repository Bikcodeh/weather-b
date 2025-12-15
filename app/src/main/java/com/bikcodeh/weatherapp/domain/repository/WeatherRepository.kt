package com.bikcodeh.weatherapp.domain.repository

import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.data.remote.dto.LocationDto

interface WeatherRepository {
    suspend fun getSearch(query: String): Result<List<LocationDto>>
    suspend fun getForecast(query: String, days: String): Result<ForecastResponseDto>
}