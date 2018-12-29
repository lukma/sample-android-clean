package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.entity.AuthResponse
import com.lukma.clean.domain.auth.Auth

object AuthMapper {
    fun transform(value: AuthResponse) = Auth(
        String(),
        value.token.accessToken,
        value.token.refreshToken,
        value.token.tokenType
    )
}