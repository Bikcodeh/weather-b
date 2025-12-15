package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.ConditionDto
import com.bikcodeh.weatherapp.data.remote.dto.DayDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastDayDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForecastDayDtoMapperTest {
    @Test
    fun `toUiModel maps forecast day correctly when data is complete`() {
        val dto = ForecastDayDto(
            date = "2025-12-16",
            day = DayDto(
                maxtempC = 22.5,
                mintempC = 14.0,
                condition = ConditionDto(
                    text = "Sunny",
                    icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"
                )
            )
        )

        val result = dto.toUiModel()

        assertThat(result.date).isEqualTo("2025-12-16")
        assertThat(result.maxTemperatureCelsius).isEqualTo(22.5)
        assertThat(result.minTemperatureCelsius).isEqualTo(14.0)
        assertThat(result.condition).isEqualTo("Sunny")
        assertThat(result.weatherIcon)
            .isEqualTo("https://cdn.weatherapi.com/weather/64x64/day/113.png")
    }

    @Test
    fun `toUiModel supports null temperatures`() {
        val dto = ForecastDayDto(
            date = "2025-12-18",
            day = DayDto(
                maxtempC = null,
                mintempC = null,
                condition = ConditionDto(
                    text = "Cloudy",
                    icon = "//icon.png"
                )
            )
        )

        val result = dto.toUiModel()

        assertThat(result.maxTemperatureCelsius).isNull()
        assertThat(result.minTemperatureCelsius).isNull()
        assertThat(result.condition).isEqualTo("Cloudy")
    }

    @Test
    fun `toUiModel returns null icon when condition is null`() {
        val dto = ForecastDayDto(
            date = "2025-12-19",
            day = DayDto(
                maxtempC = 18.0,
                mintempC = 10.0,
                condition = null
            )
        )

        val result = dto.toUiModel()

        assertThat(result.weatherIcon).isNull()
    }
}