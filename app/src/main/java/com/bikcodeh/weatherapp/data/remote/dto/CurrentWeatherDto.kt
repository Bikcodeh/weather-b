package com.bikcodeh.weatherapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherDto(
    @Json(name = "temp_c")
    val tempC: Double?,

    @field:Json(name = "feelslike_c")
    val feelslikeC: Double?,

    val humidity: Long?,

    @field:Json(name = "wind_kph")
    val windKph: Double?,

    @field:Json(name = "wind_dir")
    val windDir: String?,

    @field:Json(name = "precip_mm")
    val precipMm: Double?,

    @field:Json(name = "vis_km")
    val visKm: Double?,

    val condition: ConditionDto?
)
