package com.bikcodeh.weatherapp.feature.detail.model

import com.bikcodeh.weatherapp.core.testing.data.TestData
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class WeatherDetailUiModelTest {

    private val uiModel = TestData.bogotaForecast.toUiModel()

    @Test
    fun `current values are rounded for display`() {
        assertThat(uiModel.temperatureCelsius).isEqualTo(19) // 18.6
        assertThat(uiModel.feelsLikeCelsius).isEqualTo(17) // 17.4
        assertThat(uiModel.windSpeedKph).isEqualTo(11) // 11.2
        assertThat(uiModel.precipitationMm).isEqualTo(0.4)
        assertThat(uiModel.humidityPercent).isEqualTo(63)
    }

    @Test
    fun `today comes from the first forecast day`() {
        assertThat(uiModel.locationName).isEqualTo("Bogota")
        assertThat(uiModel.date).isEqualTo(LocalDate.of(2025, 12, 16))
        assertThat(uiModel.condition).isEqualTo("Partly cloudy")
    }

    @Test
    fun `upcoming days exclude today`() {
        assertThat(uiModel.upcomingDays).containsExactly(
            ForecastDayUiModel(
                date = LocalDate.of(2025, 12, 17),
                maxTemperatureCelsius = 22, // 21.6
                minTemperatureCelsius = 10, // 9.5
                condition = "Sunny",
                iconUrl = TestData.sunny.iconUrl,
            ),
            ForecastDayUiModel(
                date = LocalDate.of(2025, 12, 18),
                maxTemperatureCelsius = 19, // 19.4
                minTemperatureCelsius = 8, // 7.6
                condition = "Sunny",
                iconUrl = TestData.sunny.iconUrl,
            ),
        )
    }

    @Test
    fun `a forecast without days has no date nor upcoming days`() {
        val forecast = TestData.bogotaForecast.copy(days = emptyList()).toUiModel()

        assertThat(forecast.date).isNull()
        assertThat(forecast.upcomingDays).isEmpty()
    }
}
