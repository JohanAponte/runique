package com.example.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.+)"
        return email.matches(emailRegex.toRegex())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length < MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasDigit,
            hasUpperCaseCharacter = hasUppercase,
            hasLowerCaseCharacter = hasLowercase,
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}