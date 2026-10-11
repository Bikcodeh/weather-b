package com.bikcodeh.weatherapp.core.ui.error

import androidx.annotation.StringRes
import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.ui.R

/**
 * User-facing message for a [DomainError]. ViewModels expose the resource id (not a String) so
 * they stay free of `Context` and the text follows configuration changes such as the locale.
 */
@StringRes
fun DomainError.messageRes(): Int = when (this) {
    DomainError.Network -> R.string.core_ui_error_network
    DomainError.Timeout -> R.string.core_ui_error_timeout
    DomainError.Unauthorized -> R.string.core_ui_error_unauthorized
    DomainError.NotFound -> R.string.core_ui_error_not_found
    DomainError.Server -> R.string.core_ui_error_server
    DomainError.Parsing -> R.string.core_ui_error_parsing
    DomainError.Unknown -> R.string.core_ui_error_unknown
}
