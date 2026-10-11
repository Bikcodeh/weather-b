package com.bikcodeh.weatherapp.core.data.error

import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ErrorMappingTest {

    @Test
    fun `safeApiCall wraps the value of a successful call`() = runTest {
        assertThat(safeApiCall { 42 }).isEqualTo(DomainResult.Success(42))
    }

    @Test
    fun `safeApiCall rethrows cancellation instead of reporting a failure`() = runTest {
        val error = runCatching {
            safeApiCall { throw CancellationException("cancelled") }
        }.exceptionOrNull()

        assertThat(error).isInstanceOf(CancellationException::class.java)
    }

    @Test
    fun `exceptions are mapped to domain errors`() {
        assertThat(SocketTimeoutException().toDomainError()).isEqualTo(DomainError.Timeout)
        assertThat(UnknownHostException().toDomainError()).isEqualTo(DomainError.Network)
        assertThat(IOException().toDomainError()).isEqualTo(DomainError.Network)
        assertThat(SerializationException().toDomainError()).isEqualTo(DomainError.Parsing)
        assertThat(IllegalStateException().toDomainError()).isEqualTo(DomainError.Unknown)
    }
}
