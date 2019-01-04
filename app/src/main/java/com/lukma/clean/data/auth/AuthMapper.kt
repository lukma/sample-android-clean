package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.entity.response.AuthResponse
import com.lukma.clean.data.auth.entity.table.AuthTable

object AuthMapper {
    fun transform(value: AuthResponse) = AuthTable(
        String(),
        value.token.accessToken,
        value.token.refreshToken,
        value.token.tokenType
    )
}
