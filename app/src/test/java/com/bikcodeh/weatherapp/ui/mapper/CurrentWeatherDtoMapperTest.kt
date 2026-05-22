package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.mapper.toDomain
import com.bikcodeh.weatherapp.data.remote.dto.ConditionDto
import com.bikcodeh.weatherapp.data.remote.dto.CurrentWeatherDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CurrentWeatherDtoMapperTest {

    @Test
    fun `toDomain maps current weather dto to domain model correctly`() {
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

        val result = dto.toDomain()

        assertThat(result.tempC).isEqualTo(6.9)
        assertThat(result.feelslikeC).isEqualTo(4.8)
        assertThat(result.humidity).isEqualTo(42L)
        assertThat(result.condition.text).isEqualTo("Clear")
        assertThat(result.condition.icon).isEqualTo("//icon.png")
    }

    @Test
    fun `toDomain handles null values by providing defaults`() {
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

        val result = dto.toDomain()

        assertThat(result.tempC).isEqualTo(0.0)
        assertThat(result.humidity).isEqualTo(0L)
        assertThat(result.windDir).isEmpty()
        assertThat(result.condition.text).isEmpty()
    }
}
