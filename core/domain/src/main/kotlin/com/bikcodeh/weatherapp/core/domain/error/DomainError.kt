package com.bikcodeh.weatherapp.core.domain.error

/**
 * Failures exposed by the domain layer, independent of the technology that produced them
 * (HTTP, serialization, database...). The data layer translates its exceptions into these values.
 */
sealed interface DomainError {

    /** There is no internet connection or the server cannot be reached. */
    data object Network : DomainError

    /** The server took too long to respond. */
    data object Timeout : DomainError

    /** The request was rejected: invalid or disabled API key, or the quota was exceeded. */
    data object Unauthorized : DomainError

    /** The requested resource (e.g. a location) does not exist. */
    data object NotFound : DomainError

    /** The server failed while processing a valid request. */
    data object Server : DomainError

    /** The response could not be understood. */
    data object Parsing : DomainError

    /** Any failure not covered by the cases above. */
    data object Unknown : DomainError
}
