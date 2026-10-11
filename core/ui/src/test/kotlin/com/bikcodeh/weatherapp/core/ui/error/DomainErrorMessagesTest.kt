package com.bikcodeh.weatherapp.core.ui.error

import com.bikcodeh.weatherapp.core.domain.error.DomainError
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DomainErrorMessagesTest {

    @Test
    fun `every domain error has its own message`() {
        val errors = listOf(
            DomainError.Network,
            DomainError.Timeout,
            DomainError.Unauthorized,
            DomainError.NotFound,
            DomainError.Server,
            DomainError.Parsing,
            DomainError.Unknown,
        )

        val messages = errors.map { it.messageRes() }

        assertThat(messages).doesNotHaveDuplicates()
    }
}
