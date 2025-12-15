package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LocationDtoMapperTest {

    @Test
    fun `toUiModel maps location dto correctly`() {
        // GIVEN
        val dto = LocationDto(
            id = 123,
            name = "Bogota",
            country = "Colombia",
            region = "Cundinamarca",
        )

        // WHEN
        val uiModel = dto.toUiModel()

        // THEN
        assertThat(uiModel.id).isEqualTo(123)
        assertThat(uiModel.name).isEqualTo("Bogota")
        assertThat(uiModel.country).isEqualTo("Colombia")
        assertThat(uiModel.region).isEqualTo("Cundinamarca")
    }

}