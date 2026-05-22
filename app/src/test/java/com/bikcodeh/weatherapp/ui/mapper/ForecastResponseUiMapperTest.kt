package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForecastResponseUiMapperTest {

    @Test
    fun `toUiModel maps domain model to ui model correctly`() {
        // GIVEN
        val domain = WeatherForecast(
            location = Location(
                name = "Bogota",
                country = "Colombia",
                region = "Cundinamarca",
            ),
            current = CurrentWeather(
                tempC = 18.0,
                feelslikeC = 17.0,
                humidity = 60,
                windKph = 12.0,
                windDir = "NE",
                precipMm = 0.0,
                visKm = 10.0,
                condition = Condition(
                    text = "Clear",
                    icon = "icon_current"
                )
            ),
            forecast = listOf(
                ForecastDay(
                    date = "2025-12-15",
                    maxTempC = 22.0,
                    minTempC = 14.0,
                    condition = Condition(
                        text = "Sunny",
                        icon = "icon_day_1"
                    )
                )
            )
        )

        // WHEN
        val uiModel = domain.toUiModel()

        // THEN
        assertThat(uiModel.current.location).isEqualTo("Bogota")
        assertThat(uiModel.current.temperatureCelsius).isEqualTo(18.0)
        assertThat(uiModel.forecastDays).hasSize(1)
        assertThat(uiModel.forecastDays[0].condition).isEqualTo("Sunny")
    }
}
