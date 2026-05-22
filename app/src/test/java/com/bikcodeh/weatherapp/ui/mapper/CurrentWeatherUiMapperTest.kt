package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.Condition
import com.bikcodeh.weatherapp.domain.model.CurrentWeather
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CurrentWeatherUiMapperTest {

    @Test
    fun `toUiModel maps all fields correctly from domain model`() {
        val domainModel = CurrentWeather(
            tempC = 6.9,
            feelslikeC = 4.8,
            humidity = 42,
            windKph = 10.8,
            windDir = "SSE",
            precipMm = 0.5,
            visKm = 10.0,
            condition = Condition(
                text = "Clear",
                icon = "//icon.png"
            )
        )

        val result = domainModel.toUiModel(location = "Bogota")

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
}
