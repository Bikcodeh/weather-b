package com.bikcodeh.weatherapp.data.remote.network

import com.bikcodeh.weatherapp.domain.error.Failure
import com.bikcodeh.weatherapp.domain.error.toFailure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Allows to make a request in a safe way catching possible errors
 * and sending report to app center and prints log with timber
 * @return Result<T>: returns a Result wrapper with the given expected data
 */
suspend fun <T : Any> makeSafeRequest(
    execute: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = execute()
        val body = response.body()

        if (response.isSuccessful && body != null) {
            Result.success(body)
        } else {
            Result.failure(Failure.Server(response.code()))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
