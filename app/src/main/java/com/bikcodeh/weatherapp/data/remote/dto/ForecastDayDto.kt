package com.bikcodeh.weatherapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastDayDto(
    val date: String,
    val day: DayDto
)