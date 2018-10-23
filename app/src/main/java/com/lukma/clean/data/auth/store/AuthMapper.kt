package com.lukma.clean.data.auth.store

import com.lukma.clean.data.auth.Auth
import com.lukma.clean.data.auth.store.cloud.response.AuthResponse
import com.lukma.clean.data.auth.store.database.AuthTable

class AuthMapper {
    fun transform(value: Auth) = AuthTable(
            value.username,
            value.accessToken,
            value.refreshToken,
            value.tokenType,
            value.isActive
    )

    fun transform(value: AuthResponse) = Auth(
            "",
            value.token.accessToken,
            value.token.refreshToken,
            value.token.tokenType
    )

    fun transform(value: AuthTable) = Auth(
            value.username,
            value.accessToken,
            value.refreshToken,
            value.tokenType,
            value.isActive
    )

    fun transform(values: List<AuthTable>) = values.map { transform(it) }
}