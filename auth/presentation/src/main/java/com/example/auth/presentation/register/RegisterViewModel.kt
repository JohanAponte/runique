package com.example.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.UserDataValidator
import com.example.core.presentation.ui.textAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    init {
        state.email.textAsFlow().onEach { email ->
            val isEmailValid = userDataValidator.isValidEmail(email = email.toString())
            state = state.copy(
                isEmailValid = isEmailValid,
                canRegister = isEmailValid && state.passwordValidationState.isValidPassword
                        && !state.isRegistering
            )
        }.launchIn(viewModelScope)

        state.password.textAsFlow().onEach { password ->
            val passwordValidationState =
                userDataValidator.validatePassword(password = password.toString())
            state = state.copy(
                passwordValidationState = passwordValidationState,
                canRegister = state.isEmailValid && passwordValidationState.isValidPassword
                        && !state.isRegistering
            )
        }.launchIn(viewModelScope)
    }

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