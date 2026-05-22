package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.mapper.toDomain
import com.bikcodeh.weatherapp.data.remote.dto.ConditionDto
import com.bikcodeh.weatherapp.data.remote.dto.DayDto
import com.bikcodeh.weatherapp.data.remote.dto.ForecastDayDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForecastDayDtoMapperTest {
    @Test
    fun `toDomain maps forecast day dto correctly when data is complete`() {
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

        val result = dto.toDomain()

        assertThat(result.date).isEqualTo("2025-12-16")
        assertThat(result.maxTempC).isEqualTo(22.5)
        assertThat(result.minTempC).isEqualTo(14.0)
        assertThat(result.condition.text).isEqualTo("Sunny")
        assertThat(result.condition.icon).isEqualTo("//cdn.weatherapi.com/weather/64x64/day/113.png")
    }

    @Test
    fun `toDomain handles null values by providing defaults`() {
        val dto = ForecastDayDto(
            date = "2025-12-18",
            day = DayDto(
                maxtempC = null,
                mintempC = null,
                condition = null
            )
        )

        val result = dto.toDomain()

        assertThat(result.maxTempC).isEqualTo(0.0)
        assertThat(result.minTempC).isEqualTo(0.0)
        assertThat(result.condition.text).isEmpty()
        assertThat(result.condition.icon).isEmpty()
    }
}
