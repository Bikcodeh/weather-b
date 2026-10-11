package com.bikcodeh.weatherapp.core.data.error

import com.bikcodeh.weatherapp.core.domain.error.DomainError
import com.bikcodeh.weatherapp.core.domain.result.DomainResult
import java.io.IOException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import java.net.SocketTimeoutException
import java.time.format.DateTimeParseException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import timber.log.Timber

/**
 * Runs a remote call and wraps its outcome in a [DomainResult], translating any exception into a
 * [DomainError] so no framework exception crosses the data layer boundary.
 *
 * [CancellationException] is rethrown: swallowing it would break structured concurrency, e.g. a
 * cancelled search would be reported as an error instead of being silently dropped.
 */
internal suspend fun <T> safeApiCall(call: suspend () -> T): DomainResult<T> = try {
    DomainResult.Success(call())
} catch (exception: CancellationException) {
    throw exception
} catch (exception: Exception) {
    Timber.w(exception, "Remote call failed")
    DomainResult.Failure(exception.toDomainError())
}

/**
 * Maps network, HTTP and parsing exceptions to the domain error they represent.
 * [SocketTimeoutException] is checked before [IOException] because it is a subclass of it.
 */
internal fun Throwable.toDomainError(): DomainError = when (this) {
    is SocketTimeoutException -> DomainError.Timeout
    is IOException -> DomainError.Network
    is HttpException -> httpStatusToDomainError(code())
    is SerializationException, is DateTimeParseException -> DomainError.Parsing
    else -> DomainError.Unknown
}

/**
 * WeatherAPI semantics: 401 means a missing/invalid key, 403 a disabled key or exceeded quota,
 * and 400 is returned (with error code 1006) when no location matches the query.
 */
private fun httpStatusToDomainError(status: Int): DomainError = when (status) {
    HTTP_UNAUTHORIZED, HTTP_FORBIDDEN -> DomainError.Unauthorized
    HTTP_BAD_REQUEST, HTTP_NOT_FOUND -> DomainError.NotFound
    in 500..599 -> DomainError.Server
    else -> DomainError.Unknown
}
