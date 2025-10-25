package com.example.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is RegisterAction.OnLoginClick -> {
                // Handle login click
            }

            is RegisterAction.OnRegisterClick -> {
                // Handle register click
            }
        }
    }
}