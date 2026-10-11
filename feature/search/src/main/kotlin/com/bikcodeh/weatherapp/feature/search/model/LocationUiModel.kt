package com.bikcodeh.weatherapp.feature.search.model

import com.bikcodeh.weatherapp.core.domain.model.Location

/**
 * A search result ready to be displayed.
 *
 * @property subtitle Region and country, used to tell apart places with the same name.
 */
internal data class LocationUiModel(
    val id: Long,
    val name: String,
    val subtitle: String,
)

internal fun Location.toUiModel(): LocationUiModel = LocationUiModel(
    id = id,
    name = name,
    subtitle = listOf(region, country)
        .filter(String::isNotBlank)
        .joinToString(separator = ", "),
)
