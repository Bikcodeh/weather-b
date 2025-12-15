package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.ConditionDto
import com.bikcodeh.weatherapp.data.remote.dto.CurrentWeatherDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CurrentWeatherDtoMapperTest {

    @Test
    fun `toUiModel maps all fields correctly when dto is complete`() {
        val dto = CurrentWeatherDto(
            tempC = 6.9,
            feelslikeC = 4.8,
            humidity = 42,
            windKph = 10.8,
            windDir = "SSE",
            precipMm = 0.5,
            visKm = 10.0,
            condition = ConditionDto(
                text = "Clear",
                icon = "//icon.png"
            )
        )

        val result = dto.toUiModel(location = "Bogota")

        assertThat(result.location).isEqualTo("Bogota")
        assertThat(result.temperatureCelsius).isEqualTo(6.9)
        assertThat(result.feelsLikeCelsius).isEqualTo(4.8)
        assertThat(result.humidityPercentage).isEqualTo(42)
        assertThat(result.windSpeedKph).isEqualTo(10.8)
        assertThat(result.windDirection).isEqualTo("SSE")
        assertThat(result.precipitationMm).isEqualTo(0.5)
        assertThat(result.visibilityKm).isEqualTo(10)
        assertThat(result.weatherConditionText).isEqualTo("Clear")
        assertThat(result.weatherConditionIconUrl).isEqualTo("//icon.png")
    }

    @Test
    fun `toUiModel uses default values when dto fields are null`() {
        val dto = CurrentWeatherDto(
            tempC = null,
            feelslikeC = null,
            humidity = null,
            windKph = null,
            windDir = null,
            precipMm = null,
            visKm = null,
            condition = null
        )

        val result = dto.toUiModel(location = "Medellin")

        assertThat(result.location).isEqualTo("Medellin")
        assertThat(result.temperatureCelsius).isEqualTo(0.0)
        assertThat(result.feelsLikeCelsius).isEqualTo(0.0)
        assertThat(result.humidityPercentage).isEqualTo(0)
        assertThat(result.windSpeedKph).isEqualTo(0.0)
        assertThat(result.windDirection).isEqualTo("")
        assertThat(result.precipitationMm).isEqualTo(0.0)
        assertThat(result.visibilityKm).isEqualTo(0L)
        assertThat(result.weatherConditionText).isEqualTo("")
        assertThat(result.weatherConditionIconUrl).isEqualTo("")
    }

    @Test
    fun `toUiModel uses tempC as fallback for feelsLikeCelsius`() {
        val dto = CurrentWeatherDto(
            tempC = 12.5,
            feelslikeC = null,
            humidity = 50,
            windKph = 5.0,
            windDir = "N",
            precipMm = 1.0,
            visKm = 8.0,
            condition = ConditionDto(
                text = "Cloudy",
                icon = "icon"
            )
        )

        val result = dto.toUiModel(location = "Cali")

        assertThat(result.feelsLikeCelsius).isEqualTo(12.5)
    }


}