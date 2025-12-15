package com.bikcodeh.weatherapp.domain.error

fun Throwable.toFailure(): Failure {
    return when (this) {
        is java.net.UnknownHostException,
        is java.net.ConnectException,
        is java.util.concurrent.TimeoutException -> Failure.Network

        is NullPointerException,
        is com.squareup.moshi.JsonDataException,
        is com.squareup.moshi.JsonEncodingException -> Failure.Parsing

        is retrofit2.HttpException -> Failure.Server(this.code())

        else -> Failure.Unknown
    }
}