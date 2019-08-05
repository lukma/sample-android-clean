package com.lukma.android.domain.auth.entity

data class Auth(
    val username: String,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val isActive: Boolean
)
