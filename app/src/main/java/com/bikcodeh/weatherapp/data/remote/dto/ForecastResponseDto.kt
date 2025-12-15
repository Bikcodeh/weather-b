package com.bikcodeh.weatherapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponseDto(
    val location: LocationForecastDto,
    val current: CurrentWeatherDto,
    val forecast: ForecastDto
)