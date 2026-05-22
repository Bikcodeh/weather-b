package com.bikcodeh.weatherapp.domain.repository

import com.bikcodeh.weatherapp.domain.model.Location
import com.bikcodeh.weatherapp.domain.model.WeatherForecast

interface WeatherRepository {
    suspend fun getSearch(query: String): Result<List<Location>>
    suspend fun getForecast(query: String, days: String): Result<WeatherForecast>
}
