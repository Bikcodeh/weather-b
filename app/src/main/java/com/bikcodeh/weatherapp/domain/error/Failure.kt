package com.bikcodeh.weatherapp.domain.error

sealed class Failure: Throwable() {
    object Network : Failure()
    data class Server(val code: Int) : Failure()
    object Parsing : Failure()
    object Unknown : Failure()
}