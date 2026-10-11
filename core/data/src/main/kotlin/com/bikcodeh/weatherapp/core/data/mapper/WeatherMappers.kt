package com.bikcodeh.weatherapp.core.data.mapper

import com.bikcodeh.weatherapp.core.data.remote.dto.ConditionDto
import com.bikcodeh.weatherapp.core.data.remote.dto.CurrentWeatherDto
import com.bikcodeh.weatherapp.core.data.remote.dto.ForecastDayDto
import com.bikcodeh.weatherapp.core.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.core.data.remote.dto.LocationDto
import com.bikcodeh.weatherapp.core.domain.model.CurrentWeather
import com.bikcodeh.weatherapp.core.domain.model.ForecastDay
import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.model.WeatherCondition
import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import java.time.LocalDate

// DTO -> domain mappers. They are the only place that knows about the API's quirks.

internal fun LocationDto.toDomain(): Location = Location(
    id = id,
    name = name,
    region = region,
    country = country,
)

/**
 * The forecast endpoint does not return the location id, so the id used in the request is
 * passed in to keep the domain [Location] complete.
 *
 * @throws java.time.format.DateTimeParseException if a forecast date is malformed.
 */
internal fun ForecastResponseDto.toDomain(locationId: Long): WeatherForecast = WeatherForecast(
    location = Location(
        id = locationId,
        name = location.name,
        region = location.region,
        country = location.country,
    ),
    current = current.toDomain(),
    days = forecast.forecastDays.map(ForecastDayDto::toDomain),
)

internal fun CurrentWeatherDto.toDomain(): CurrentWeather = CurrentWeather(
    temperatureCelsius = temperatureCelsius,
    feelsLikeCelsius = feelsLikeCelsius,
    humidityPercent = humidity,
    windSpeedKph = windKph,
    precipitationMm = precipitationMm,
    condition = condition.toDomain(),
)

internal fun ForecastDayDto.toDomain(): ForecastDay = ForecastDay(
    date = LocalDate.parse(date),
    maxTemperatureCelsius = day.maxTemperatureCelsius,
    minTemperatureCelsius = day.minTemperatureCelsius,
    condition = day.condition.toDomain(),
)

internal fun ConditionDto.toDomain(): WeatherCondition = WeatherCondition(
    description = text,
    iconUrl = icon.toAbsoluteUrl(),
)

/** WeatherAPI returns protocol-relative icon URLs (`//cdn...`); image loaders need a scheme. */
private fun String.toAbsoluteUrl(): String = if (startsWith("//")) "https:$this" else this
