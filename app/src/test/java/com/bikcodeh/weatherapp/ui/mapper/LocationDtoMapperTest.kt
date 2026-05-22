package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.mapper.toDomain
import com.bikcodeh.weatherapp.data.remote.dto.LocationDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LocationDtoMapperTest {

    @Test
    fun `toDomain maps location dto to domain correctly`() {
        // GIVEN
        val dto = LocationDto(
            id = 123,
            name = "Bogota",
            country = "Colombia",
            region = "Cundinamarca",
        )

        // WHEN: Ahora probamos el mapeo al dominio (Capa de Data -> Dominio)
        val domainModel = dto.toDomain()

        // THEN
        assertThat(domainModel.id).isEqualTo(123)
        assertThat(domainModel.name).isEqualTo("Bogota")
        assertThat(domainModel.country).isEqualTo("Colombia")
        assertThat(domainModel.region).isEqualTo("Cundinamarca")
    }
}
