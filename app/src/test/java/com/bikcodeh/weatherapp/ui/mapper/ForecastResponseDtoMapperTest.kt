package com.bikcodeh.weatherapp.ui.mapper

import com.bikcodeh.weatherapp.data.mapper.toDomain
import com.bikcodeh.weatherapp.data.remote.dto.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ForecastResponseDtoMapperTest  {

    @Test
    fun `toDomain maps forecast response dto to domain correctly`() {
        // GIVEN
        val response = ForecastResponseDto(
            location = LocationForecastDto(
                name = "Bogota",
                country = "Colombia",
                region = "Cundinamarca",
            ),
            current = CurrentWeatherDto(
                tempC = 18.0,
                feelslikeC = 17.0,
                humidity = 60,
                windKph = 12.0,
                windDir = "NE",
                precipMm = 0.0,
                visKm = 10.0,
                condition = ConditionDto(
                    text = "Clear",
                    icon = "icon_current"
                )
            ),
            forecast = ForecastDto(
                forecastday = listOf(
                    ForecastDayDto(
                        date = "2025-12-15",
                        day = DayDto(
                            maxtempC = 22.0,
                            mintempC = 14.0,
                            condition = ConditionDto(
                                text = "Sunny",
                                icon = "icon_day_1"
                            )
                        )
                    )
                )
            )
        )

        // WHEN
        val domainModel = response.toDomain()

        // THEN
        assertThat(domainModel.location.name).isEqualTo("Bogota")
        assertThat(domainModel.current.tempC).isEqualTo(18.0)
        assertThat(domainModel.forecast).hasSize(1)
        assertThat(domainModel.forecast[0].maxTempC).isEqualTo(22.0)
    }
}
