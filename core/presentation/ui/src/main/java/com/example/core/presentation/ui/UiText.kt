package com.example.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * A sealed interface representing different types of UI text.
 *
 * This interface is used to handle text in a flexible way, allowing for either
 * dynamic strings or string resources with optional arguments.
 */
sealed interface UiText {

    /**
     * Represents a dynamic string.
     *
     * @property value The string value.
     */
    data class DynamicString(val value: String) : UiText

    /**
     * Represents a string resource.
     *
     * @property resId The resource ID of the string.
     * @property args Optional arguments to format the string.
     */
    data class StringResource(
        @StringRes val resId: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText

    /**
     * Converts the `UiText` to a string in a Jetpack Compose context.
     *
     * @return The string representation of the `UiText`.
     */
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args)
        }
    }

    /**
     * Converts the `UiText` to a string using a given `Context`.
     *
     * @param context The Android context used to resolve the string resource.
     * @return The string representation of the `UiText`.
     */
    @Composable
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}