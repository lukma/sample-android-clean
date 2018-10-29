package com.lukma.clean.domain.auth

data class Auth(
        val username: String,
        val accessToken: String,
        val refreshToken: String,
        val tokenType: String,
        val isActive: Boolean = true
)