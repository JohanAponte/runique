package com.example.core.data.networking

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val userId: String
)
