package com.bikcodeh.weatherapp.data.remote.api

import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET("v1/search.json")
    suspend fun searchLocation(
        @Query("q") query: String
    ): Response<List<LocationDto>>

    @GET("v1/forecast.json")
    suspend fun getCurrentConditions(
        @Query("q") query: String,
        @Query("days") days: String
    ): Response<ForecastResponseDto>
}