package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.Location
import com.bikcodeh.weatherapp.ui.model.LocationUiModel

fun Location.toUiModel(): LocationUiModel =
    LocationUiModel(
        id = id,
        name = name,
        country = country,
        region = region
    )
