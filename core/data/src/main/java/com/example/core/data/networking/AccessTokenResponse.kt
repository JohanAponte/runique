package com.example.core.data.networking

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val expirationTimestamp: Long
)
