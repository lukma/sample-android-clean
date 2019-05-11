package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.response.AuthResponse
import com.lukma.clean.data.auth.local.AuthTable

object AuthMapper {
    fun transform(value: AuthResponse) = AuthTable(
        String(),
        value.token.accessToken,
        value.token.refreshToken,
        value.token.tokenType
    )
}
