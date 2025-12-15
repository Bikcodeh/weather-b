package com.bikcodeh.weatherapp.data.remote.dto

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ConditionDto(
    val text: String,
    val icon: String
)
