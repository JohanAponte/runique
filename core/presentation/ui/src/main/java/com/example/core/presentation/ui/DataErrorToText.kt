package com.example.core.presentation.ui

import com.example.core.domain.util.DataError
import com.example.core.presentation.ui.UiText.*

/**
 * Converts a `DataError` instance to a `UiText` representation.
 *
 * This function maps specific `DataError` cases to corresponding string resources
 * that can be displayed in the UI. It handles both `Local` and `NetworkError` types
 * of `DataError` and provides a generic error message for unhandled cases.
 *
 * @receiver The `DataError` instance to be converted.
 * @return A `UiText` instance representing the error message.
 */
fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> StringResource(
            R.string.error_disk_full
        )
        DataError.Network.REQUEST_TIMEOUT -> StringResource(
            R.string.error_request_timeout
        )
        DataError.Network.TOO_MANY_REQUESTS -> StringResource(
            R.string.error_too_many_requests
        )
        DataError.Network.NO_INTERNET -> StringResource(
            R.string.error_no_internet
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> StringResource(
            R.string.error_payload_too_large
        )
        DataError.Network.SERVER_ERROR -> StringResource(
            R.string.error_server_error
        )
        DataError.Network.SERIALIZATION -> StringResource(
            R.string.error_serialization
        )
        DataError.Network.UNKNOWN -> StringResource(
            R.string.error_unknown
        )
        else -> StringResource(
            R.string.error_generic
        )
    }
}