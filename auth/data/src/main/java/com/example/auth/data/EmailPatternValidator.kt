package com.example.auth.data

import android.util.Patterns
import com.example.auth.domain.PatternValidator

object EmailPatternValidator: PatternValidator {
    override fun matches(input: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }
}