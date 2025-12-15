package com.bikcodeh.weatherapp.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationForecastDto(
    val country: String,
    val name: String,
    val region: String
)