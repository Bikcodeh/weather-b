package com.bikcodeh.weatherapp.ui.error

import com.bikcodeh.weatherapp.R
import com.bikcodeh.weatherapp.domain.error.Failure

fun Failure.toMessageRes(): Int {
    return when (this) {
        Failure.Network -> R.string.error_connection
        Failure.Parsing -> R.string.error_parsing
        is Failure.Server -> when (this.code) {
            404 -> R.string.error_not_found_error
            403 -> R.string.error_unauthorized_error
            500 -> R.string.error_server
            else -> R.string.error_unknown
        }

        Failure.Unknown -> R.string.error_unknown
    }
}