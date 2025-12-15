package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.ConditionDto
import com.bikcodeh.weatherapp.data.remote.dto.CurrentWeatherDto
import com.bikcodeh.weatherapp.data.remote.dto.DayDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastDayDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastResponseDto
import com.bikcodeh.weatherapp.data.remote.dto.LocationForecastDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForecastResponseDtoMapperTest  {

    @Test
    fun `toUiModel maps current weather and forecast days correctly`() {
        // GIVEN
        val response = ForecastResponseDto(
            location = LocationForecastDto(
                name = "Bogota",
                country = "Colombia",
                region = "Cundinamarca",
            ),
            current = CurrentWeatherDto(
                tempC = 18.0,
                feelslikeC = 17.0,
                humidity = 60,
                windKph = 12.0,
                windDir = "NE",
                precipMm = 0.0,
                visKm = 10.0,
                condition = ConditionDto(
                    text = "Clear",
                    icon = "icon_current"
                )
            ),
            forecast = ForecastDto(
                forecastday = listOf(
                    ForecastDayDto(
                        date = "2025-12-15",
                        day = DayDto(
                            maxtempC = 22.0,
                            mintempC = 14.0,
                            condition = ConditionDto(
                                text = "Sunny",
                                icon = "icon_day_1"
                            )
                        )
                    ),
                    ForecastDayDto(
                        date = "2025-12-16",
                        day = DayDto(
                            maxtempC = 20.0,
                            mintempC = 13.0,
                            condition = ConditionDto(
                                text = "Cloudy",
                                icon = "icon_day_2"
                            )
                        )
                    )
                )
            )
        )

        // WHEN
        val uiModel = response.toUiModel()

        // THEN
        assertThat(uiModel.current.location).isEqualTo("Bogota")
        assertThat(uiModel.current.temperatureCelsius).isEqualTo(18.0)
        assertThat(uiModel.current.weatherConditionText).isEqualTo("Clear")

        assertThat(uiModel.forecastDays).hasSize(2)

        assertThat(uiModel.forecastDays[0].maxTemperatureCelsius).isEqualTo(22.0)
        assertThat(uiModel.forecastDays[0].minTemperatureCelsius).isEqualTo(14.0)
        assertThat(uiModel.forecastDays[0].condition).isEqualTo("Sunny")

        assertThat(uiModel.forecastDays[1].condition).isEqualTo("Cloudy")
    }

    @Test
    fun `toUiModel handles empty forecast list`() {
        val response = ForecastResponseDto(
            location = LocationForecastDto(
                name = "Medellin",
                country = "Colombia",
                region = "Antioquia",
            ),
            current = CurrentWeatherDto(
                tempC = 20.0,
                feelslikeC = 20.0,
                humidity = 50,
                windKph = 5.0,
                windDir = "N",
                precipMm = 0.0,
                visKm = 10.0,
                condition = ConditionDto(
                    text = "Sunny",
                    icon = "icon"
                )
            ),
            forecast = ForecastDto(
                forecastday = emptyList()
            )
        )

        val uiModel = response.toUiModel()

        assertThat(uiModel.forecastDays).isEmpty()
        assertThat(uiModel.current.location).isEqualTo("Medellin")
    }

    @Test
    fun `toUiModel passes location name to current ui model`() {
        val response = ForecastResponseDto(
            location = LocationForecastDto(
                name = "Cali",
                country = "Colombia",
                region = "Valle"
            ),
            current = CurrentWeatherDto(
                tempC = 25.0,
                feelslikeC = 26.0,
                humidity = 40,
                windKph = 6.0,
                windDir = "S",
                precipMm = 0.0,
                visKm = 10.0,
                condition = ConditionDto(
                    text = "Hot",
                    icon = "icon"
                )
            ),
            forecast = ForecastDto(emptyList())
        )

        val uiModel = response.toUiModel()

        assertThat(uiModel.current.location).isEqualTo("Cali")
    }
}