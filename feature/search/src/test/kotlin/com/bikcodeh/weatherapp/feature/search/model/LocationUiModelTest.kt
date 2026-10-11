package com.bikcodeh.weatherapp.feature.search.model

import com.bikcodeh.weatherapp.core.domain.model.Location
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LocationUiModelTest {

    @Test
    fun `the subtitle joins region and country`() {
        val location =
            Location(id = 1, name = "Yumbo", region = "Valle del Cauca", country = "Colombia")

        assertThat(location.toUiModel()).isEqualTo(
            LocationUiModel(id = 1, name = "Yumbo", subtitle = "Valle del Cauca, Colombia"),
        )
    }

    @Test
    fun `blank parts are left out of the subtitle`() {
        val location = Location(id = 1, name = "Monaco", region = " ", country = "Monaco")

        assertThat(location.toUiModel().subtitle).isEqualTo("Monaco")
    }
}
