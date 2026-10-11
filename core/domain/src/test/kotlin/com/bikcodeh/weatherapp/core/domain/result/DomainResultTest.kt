package com.bikcodeh.weatherapp.core.domain.result

import com.bikcodeh.weatherapp.core.domain.error.DomainError
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DomainResultTest {

    @Test
    fun `map transforms the value of a success`() {
        val result: DomainResult<Int> = DomainResult.Success(2)

        assertThat(result.map { it * 10 }).isEqualTo(DomainResult.Success(20))
    }

    @Test
    fun `map keeps failures untouched`() {
        val result: DomainResult<Int> = DomainResult.Failure(DomainError.Server)

        assertThat(result.map { it * 10 }).isEqualTo(DomainResult.Failure(DomainError.Server))
    }

    @Test
    fun `fold handles both branches`() {
        val describe: (DomainResult<Int>) -> String = { result ->
            result.fold(onSuccess = { "value $it" }, onFailure = { "error $it" })
        }

        assertThat(describe(DomainResult.Success(1))).isEqualTo("value 1")
        assertThat(describe(DomainResult.Failure(DomainError.Timeout))).isEqualTo("error Timeout")
    }
}
