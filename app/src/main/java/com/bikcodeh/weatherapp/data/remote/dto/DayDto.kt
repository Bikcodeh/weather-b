package com.bikcodeh.weatherapp.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DayDto(
    @field:Json(name = "maxtemp_c")
    val maxtempC: Double?,
    @field:Json(name = "mintemp_c")
    val mintempC: Double?,
    @field:Json(name = "condition")
    val condition: ConditionDto?
)