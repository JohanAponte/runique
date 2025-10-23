package com.example.core.data.networking

import com.example.core.data.BuildConfig
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

/**
 * Sends a GET request to the specified route with optional query parameters.
 *
 * @param Response The expected response type.
 * @param route The endpoint route to send the GET request to.
 * @param queryParameters A map of query parameters to include in the request. Defaults to an empty map.
 * @return A `Result` containing the response of type `Response` or a `DataError.Network` in case of failure.
 */
suspend inline fun <reified Response : Any> HttpClient.get(
    route: String,
    queryParameters: Map<String, String> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRout(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

/**
 * Sends a DELETE request to the specified route with optional query parameters.
 *
 * @param Response The expected response type.
 * @param route The endpoint route to send the DELETE request to.
 * @param queryParameters A map of query parameters to include in the request. Defaults to an empty map.
 * @return A `Result` containing the response of type `Response` or a `DataError.Network` in case of failure.
 */
suspend inline fun <reified Response : Any> HttpClient.delete(
    route: String,
    queryParameters: Map<String, String> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        delete {
            url(constructRout(route))
            queryParameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }
}

/**
 * Sends a POST request to the specified route with a request body.
 *
 * @param Request The type of the request body.
 * @param Response The expected response type.
 * @param route The endpoint route to send the POST request to.
 * @param body The request body to include in the POST request.
 * @return A `Result` containing the response of type `Response` or a `DataError.Network` in case of failure.
 */
suspend inline fun <reified Request, reified Response : Any> HttpClient.post(
    route: String,
    body: Request,
): Result<Response, DataError.Network> {
    return safeCall {
        post {
            url(constructRout(route))
            setBody(body)
        }
    }
}

/**
 * Executes a network call safely, handling exceptions and mapping them to `DataError.Network`.
 *
 * @param T The expected response type.
 * @param execute A lambda function that performs the network call and returns an `HttpResponse`.
 * @return A `Result` containing the response of type `T` or a `DataError.Network` in case of failure.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException)
            e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

/**
 * Maps an `HttpResponse` to a `Result` based on the HTTP status code.
 *
 * @param T The expected response type.
 * @param response The `HttpResponse` to process.
 * @return A `Result` containing the response of type `T` for successful status codes,
 * or a `DataError.Network` for error status codes.
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

/**
 * Constructs a full URL for the given route, appending the base URL if necessary.
 *
 * @param route The endpoint route to construct the full URL for.
 * @return The full URL as a string.
 */
fun constructRout(route: String): String {
    return when {
        route.contains(BuildConfig.BASE_URL) -> route
        route.startsWith("/") -> BuildConfig.BASE_URL + route
        else -> "${BuildConfig.BASE_URL}/$route"
    }
}