package com.bikcodeh.weatherapp.ui.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ExtensionTest {

    @Test
    fun `toShortDayName returns MON for monday`() {
        val result = "2025-12-16".toDayAbbreviation()
        assertThat(result).isEqualTo("TUE")
    }

    @Test
    fun `toReadableDate returns a correct date`() {
        val result = "2025-12-16".toReadableDate()
        assertThat(result).isEqualTo("Tuesday, 16 December")
    }
}