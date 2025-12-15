package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
import com.bikcodeh.weatherapp.ui.model.LocationUiModel

fun LocationDto.toUiModel(): LocationUiModel =
    LocationUiModel(
        id = id,
        name = name,
        country = country,
        region = region
    )