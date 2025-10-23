package com.example.core.domain.util

/**
 * A sealed interface representing different types of data-related errors.
 *
 * This interface is used to categorize errors that can occur during data operations,
 * such as network or local storage errors. It extends the `Error` marker interface.
 */
sealed interface DataError : Error {

    /**
     * Enum representing various network-related errors.
     *
     * These errors occur during network operations and include cases such as
     * timeouts, unauthorized access, and server issues.
     */
    enum class Network : DataError {
        /** The request timed out. */
        REQUEST_TIMEOUT,

        /** The request was unauthorized. */
        UNAUTHORIZED,

        /** There was a conflict with the current state of the resource. */
        CONFLICT,

        /** Too many requests were made in a short period. */
        TOO_MANY_REQUESTS,

        /** No internet connection was available. */
        NO_INTERNET,

        /** The payload size exceeded the allowed limit. */
        PAYLOAD_TOO_LARGE,

        /** A server-side error occurred. */
        SERVER_ERROR,

        /** An error occurred during data serialization or deserialization. */
        SERIALIZATION,

        /** An unknown network error occurred. */
        UNKNOWN
    }

    /**
     * Enum representing various local storage-related errors.
     *
     * These errors occur during operations involving local storage, such as
     * insufficient disk space.
     */
    enum class Local : DataError {
        /** The disk is full, preventing further operations. */
        DISK_FULL,
    }
}