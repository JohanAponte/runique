package com.example.core.domain.util

/**
 * A sealed interface representing a result that can either be a success or an error.
 *
 * @param D The type of data in a successful result.
 * @param E The type of error in an error result, which must extend the `Error` class.
 */
sealed interface Result<out D, out E : Error> {

    /**
     * Represents a successful result containing data.
     *
     * @param D The type of data in the success result.
     * @property data The data contained in the success result.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents an error result containing an error.
     *
     * @param E The type of error in the error result.
     * @property error The error contained in the error result.
     */
    data class Error<out E : com.example.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

/**
 * Transforms the data in a `Result.Success` using the provided mapping function, while preserving
 * the error in a `Result.Error`.
 *
 * @param T The type of data in the original success result.
 * @param E The type of error in the result.
 * @param R The type of data in the transformed success result.
 * @param map A function that takes the original data and returns the transformed data.
 * @return A new `Result` with the transformed data if the original result was a success, or the
 *         same error if the original result was an error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> = when (this) {
    is Result.Error -> Result.Error(this.error)
    is Result.Success -> Result.Success(map(this.data))
}

/**
 * Converts a `Result` to an `EmptyDataResult`, which is a `Result` with `Unit` as the success type.
 *
 * @param T The type of data in the original success result.
 * @param E The type of error in the result.
 * @return An `EmptyDataResult` with `Unit` as the success type, preserving the original error if any.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    return map { }
}

/**
 * A type alias for a `Result` with `Unit` as the success type.
 *
 * @param E The type of error in the result.
 */
typealias EmptyDataResult<E> = Result<Unit, E>