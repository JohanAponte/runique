package com.example.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a `Flow` and handles its emissions as events within a Jetpack Compose context.
 *
 * This composable function listens to a `Flow` and invokes the provided `onEvent` callback
 * for each emitted value. It ensures that the observation is lifecycle-aware and only
 * collects the flow when the lifecycle is in the `STARTED` state.
 *
 * @param T The type of data emitted by the `Flow`.
 * @param flow The `Flow` to observe.
 * @param key1 An optional key to control recomposition. Defaults to `null`.
 * @param key2 An optional key to control recomposition. Defaults to `null`.
 * @param onEvent A callback invoked for each value emitted by the `Flow`.
 */
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { event ->
                    onEvent(event)
                }
            }
        }
    }
}