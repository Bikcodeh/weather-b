package com.bikcodeh.weatherapp.feature.detail.model

import androidx.compose.runtime.Immutable
import com.bikcodeh.weatherapp.core.domain.model.ForecastDay
import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import java.time.LocalDate
import kotlin.math.roundToInt

/**
 * Weather of a location prepared for display: temperatures and wind are rounded, and only the
 * days after today are kept for the forecast row.
 *
 * @property date Today in the location's time zone, when available.
 */
@Immutable
internal data class WeatherDetailUiModel(
    val locationName: String,
    val date: LocalDate?,
    val temperatureCelsius: Int,
    val feelsLikeCelsius: Int,
    val condition: String,
    val conditionIconUrl: String,
    val precipitationMm: Double,
    val humidityPercent: Int,
    val windSpeedKph: Int,
    val upcomingDays: List<ForecastDayUiModel>,
)

/** A future day of the forecast row. */
internal data class ForecastDayUiModel(
    val date: LocalDate,
    val maxTemperatureCelsius: Int,
    val minTemperatureCelsius: Int,
    val condition: String,
    val iconUrl: String,
)

internal fun WeatherForecast.toUiModel(): WeatherDetailUiModel = WeatherDetailUiModel(
    locationName = location.name,
    date = days.firstOrNull()?.date,
    temperatureCelsius = current.temperatureCelsius.roundToInt(),
    feelsLikeCelsius = current.feelsLikeCelsius.roundToInt(),
    condition = current.condition.description,
    conditionIconUrl = current.condition.iconUrl,
    precipitationMm = current.precipitationMm,
    humidityPercent = current.humidityPercent,
    windSpeedKph = current.windSpeedKph.roundToInt(),
    upcomingDays = days.drop(1).map(ForecastDay::toUiModel),
)

private fun ForecastDay.toUiModel(): ForecastDayUiModel = ForecastDayUiModel(
    date = date,
    maxTemperatureCelsius = maxTemperatureCelsius.roundToInt(),
    minTemperatureCelsius = minTemperatureCelsius.roundToInt(),
    condition = condition.description,
    iconUrl = condition.iconUrl,
)
