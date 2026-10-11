package com.bikcodeh.weatherapp.core.testing.data

import com.bikcodeh.weatherapp.core.domain.model.CurrentWeather
import com.bikcodeh.weatherapp.core.domain.model.ForecastDay
import com.bikcodeh.weatherapp.core.domain.model.Location
import com.bikcodeh.weatherapp.core.domain.model.WeatherCondition
import com.bikcodeh.weatherapp.core.domain.model.WeatherForecast
import java.time.LocalDate

/** Domain fixtures shared by the test suites of every module. */
object TestData {

    val bogota = Location(
        id = 2_805_431,
        name = "Bogota",
        region = "Cundinamarca",
        country = "Colombia",
    )

    val sunny = WeatherCondition(
        description = "Sunny",
        iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/113.png",
    )

    val partlyCloudy = WeatherCondition(
        description = "Partly cloudy",
        iconUrl = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
    )

    val bogotaForecast = WeatherForecast(
        location = bogota,
        current = CurrentWeather(
            temperatureCelsius = 18.6,
            feelsLikeCelsius = 17.4,
            humidityPercent = 63,
            windSpeedKph = 11.2,
            precipitationMm = 0.4,
            condition = partlyCloudy,
        ),
        days = listOf(
            ForecastDay(
                date = LocalDate.of(2025, 12, 16),
                maxTemperatureCelsius = 20.1,
                minTemperatureCelsius = 8.4,
                condition = partlyCloudy,
            ),
            ForecastDay(
                date = LocalDate.of(2025, 12, 17),
                maxTemperatureCelsius = 21.6,
                minTemperatureCelsius = 9.5,
                condition = sunny,
            ),
            ForecastDay(
                date = LocalDate.of(2025, 12, 18),
                maxTemperatureCelsius = 19.4,
                minTemperatureCelsius = 7.6,
                condition = sunny,
            ),
        ),
    )
}
