package com.bikcodeh.weatherapp.data.remote.dto


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDto(
    val country: String,
    val id: Int,
    val name: String,
    val region: String
)