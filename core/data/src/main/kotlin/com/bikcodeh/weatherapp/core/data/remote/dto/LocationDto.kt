package com.bikcodeh.weatherapp.core.data.remote.dto

import kotlinx.serialization.Serializable

/** Item of the `search.json` response. */
@Serializable
internal data class LocationDto(
    val id: Long,
    val name: String,
    val region: String = "",
    val country: String = "",
)
