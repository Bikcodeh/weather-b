package com.bikcodeh.weatherapp.core.data.remote.interceptor

import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import okhttp3.Request
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class LanguageInterceptorTest {

    private val server = MockWebServer()

    @Before
    fun setUp() = server.start()

    @After
    fun tearDown() = server.close()

    @Test
    fun `the user language is sent to the API`() {
        execute(LanguageInterceptor { "es" })

        assertThat(server.takeRequest().url.queryParameter("lang")).isEqualTo("es")
    }

    @Test
    fun `english is the API default and is not sent`() {
        execute(LanguageInterceptor { "en" })

        assertThat(server.takeRequest().url.queryParameter("lang")).isNull()
    }

    private fun execute(interceptor: LanguageInterceptor) {
        server.enqueue(MockResponse(body = "[]"))
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        client.newCall(Request.Builder().url(server.url("/v1/search.json")).build())
            .execute()
            .close()
    }
}
