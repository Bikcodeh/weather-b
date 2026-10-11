package com.bikcodeh.weatherapp.core.data.mapper

import com.bikcodeh.weatherapp.core.data.remote.dto.ConditionDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class WeatherMappersTest {

    @Test
    fun `protocol-relative icon urls get the https scheme`() {
        val condition = ConditionDto(
            text = "Sunny",
            icon = "//cdn.weatherapi.com/113.png",
        ).toDomain()

        assertThat(condition.iconUrl).isEqualTo("https://cdn.weatherapi.com/113.png")
    }

    @Test
    fun `absolute icon urls are kept as they are`() {
        val condition = ConditionDto(
            text = "Sunny",
            icon = "https://cdn.weatherapi.com/113.png",
        ).toDomain()

        assertThat(condition.iconUrl).isEqualTo("https://cdn.weatherapi.com/113.png")
    }
}
