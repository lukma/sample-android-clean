package com.lukma.clean.data.auth

data class Auth(
        val username: String,
        val accessToken: String,
        val refreshToken: String,
        val tokenType: String,
        val isActive: Boolean = true
)