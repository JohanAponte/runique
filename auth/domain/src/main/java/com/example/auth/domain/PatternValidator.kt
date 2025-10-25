package com.example.auth.domain

interface PatternValidator {
    fun matches(input: String): Boolean
}