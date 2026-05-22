package com.bikcodeh.weatherapp.domain.model

data class Location(
    val country: String,
    val id: Int = -1,
    val name: String,
    val region: String
)
