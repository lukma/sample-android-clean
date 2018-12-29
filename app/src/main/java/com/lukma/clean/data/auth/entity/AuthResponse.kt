package com.lukma.clean.data.auth.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class AuthResponse(
    @JsonProperty("token")
    val token: TokenResponse
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("refresh_token")
        val refreshToken: String,
        @JsonProperty("token_type")
        val tokenType: String
    )
}