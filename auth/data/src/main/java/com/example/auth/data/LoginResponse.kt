package com.example.auth.data

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: String
)