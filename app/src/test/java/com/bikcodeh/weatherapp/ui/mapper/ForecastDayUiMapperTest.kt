package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.Condition
import com.bikcodeh.weatherapp.domain.model.ForecastDay
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForecastDayUiMapperTest {
    @Test
    fun `toUiModel maps forecast day domain to ui model correctly`() {
        val domain = ForecastDay(
            date = "2025-12-16",
            maxTempC = 22.5,
            minTempC = 14.0,
            condition = Condition(
                text = "Sunny",
                icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
            )
        )

        val result = domain.toUiModel()

        assertThat(result.date).isEqualTo("2025-12-16")
        assertThat(result.maxTemperatureCelsius).isEqualTo(22.5)
        assertThat(result.minTemperatureCelsius).isEqualTo(14.0)
        assertThat(result.condition).isEqualTo("Sunny")
        assertThat(result.weatherIcon)
            .isEqualTo("https://cdn.weatherapi.com/weather/64x64/day/113.png")
    }
}
