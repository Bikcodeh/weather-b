package com.bikcodeh.weatherapp.core.domain.result

import com.bikcodeh.weatherapp.core.domain.error.DomainError

/**
 * Outcome of a domain operation: [Success] with a value or [Failure] with a typed [DomainError].
 *
 * Unlike [kotlin.Result], errors are values from a closed hierarchy, so callers handle every case
 * with an exhaustive `when` and no framework exception leaks out of the data layer.
 */
sealed interface DomainResult<out T> {

    /** The operation completed and produced [data]. */
    data class Success<out T>(
        val data: T,
    ) : DomainResult<T>

    /** The operation failed because of [error]. */
    data class Failure(
        val error: DomainError,
    ) : DomainResult<Nothing>
}

/** Transforms the value of a [DomainResult.Success], leaving failures untouched. */
inline fun <T, R> DomainResult<T>.map(transform: (T) -> R): DomainResult<R> = when (this) {
    is DomainResult.Success -> DomainResult.Success(transform(data))
    is DomainResult.Failure -> this
}

/** Reduces the result to a single value by handling both the success and the failure case. */
inline fun <T, R> DomainResult<T>.fold(onSuccess: (T) -> R, onFailure: (DomainError) -> R): R =
    when (this) {
        is DomainResult.Success -> onSuccess(data)
        is DomainResult.Failure -> onFailure(error)
    }
