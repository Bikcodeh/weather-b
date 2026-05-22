package com.bikcodeh.weatherapp.data.mapper

import com.bikcodeh.weatherapp.data.remote.dto.*
import com.bikcodeh.weatherapp.domain.model.*

fun LocationDto.toDomain(): Location {
    return Location(
        country = country,
        id = id,
        name = name,
        region = region
    )
}

fun LocationForecastDto.toDomain(): Location {
    return Location(
        country = country,
        name = name,
        region = region
    )
}

fun ConditionDto.toDomain(): Condition {
    return Condition(
        text = text,
        icon = icon
    )
}

fun CurrentWeatherDto.toDomain(): CurrentWeather {
    return CurrentWeather(
        tempC = tempC ?: 0.0,
        feelslikeC = feelslikeC ?: 0.0,
        humidity = humidity ?: 0L,
        windKph = windKph ?: 0.0,
        windDir = windDir ?: "",
        precipMm = precipMm ?: 0.0,
        visKm = visKm ?: 0.0,
        condition = condition?.toDomain() ?: Condition("", "")
    )
}

fun ForecastDayDto.toDomain(): ForecastDay {
    return ForecastDay(
        date = date,
        maxTempC = day.maxtempC ?: 0.0,
        minTempC = day.mintempC ?: 0.0,
        condition = day.condition?.toDomain() ?: Condition("", "")
    )
}

fun ForecastResponseDto.toDomain(): WeatherForecast {
    return WeatherForecast(
        location = location.toDomain(),
        current = current.toDomain(),
        forecast = forecast.forecastday.map { it.toDomain() }
    )
}
