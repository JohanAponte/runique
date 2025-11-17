package com.example.auth.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class UserDataValidatorTest {

    private lateinit var userDataValidator: UserDataValidator

    @BeforeEach
    fun setup() {
        userDataValidator = UserDataValidator(
            patternValidator = object : PatternValidator {
                override fun matches(text: String): Boolean {
                    return true
                }
            }
        )
    }

    @Test
    fun testValidateEmail() {
        //Given
        val email = "test@test.com"
        //When
        val isValid = userDataValidator.isValidEmail(email)
        //Then
        assertThat(isValid).isTrue()
    }

    @ParameterizedTest
    @CsvSource(
        "Short1, false, true, true, true",
        "nouppercase1, true, true, false, true",
        "NOLOWERCASE1, true, true, true, false",
        "NoHasNumber, true, false, true, true",
        "ValidPass1, true, true, true, true"
    )
    fun testValidatePassword(
        password: String,
        expectedMinLength: Boolean,
        expectedHasNumber: Boolean,
        expectedHasUpperCase: Boolean,
        expectedHasLowerCase: Boolean
    ) {
        val validationState = userDataValidator.validatePassword(password)

        assertThat(validationState.hasMinLength).isEqualTo(expectedMinLength)
        assertThat(validationState.hasNumber).isEqualTo(expectedHasNumber)
        assertThat(validationState.hasUpperCaseCharacter).isEqualTo(expectedHasUpperCase)
        assertThat(validationState.hasLowerCaseCharacter).isEqualTo(expectedHasLowerCase)
    }

}