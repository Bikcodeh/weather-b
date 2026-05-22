package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.domain.model.Location
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LocationUiMapperTest {

    @Test
    fun `toUiModel maps location domain model correctly`() {
        // GIVEN
        val domain = Location(
            id = 123,
            name = "Bogota",
            country = "Colombia",
            region = "Cundinamarca",
        )

        // WHEN
        val uiModel = domain.toUiModel()

        // THEN
        assertThat(uiModel.id).isEqualTo(123)
        assertThat(uiModel.name).isEqualTo("Bogota")
        assertThat(uiModel.country).isEqualTo("Colombia")
        assertThat(uiModel.region).isEqualTo("Cundinamarca")
    }
}
