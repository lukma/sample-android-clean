package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.response.AuthResponse
import com.lukma.clean.data.auth.local.AuthTable
import com.lukma.clean.domain.auth.entity.Auth
import org.koin.core.error.MissingPropertyException

fun transform(value: AuthResponse) = value.let {
    Auth(
        String(),
        it.token?.accessToken ?: throw MissingPropertyException("accessToken"),
        it.token.refreshToken ?: throw MissingPropertyException("refreshToken"),
        it.token.tokenType ?: throw MissingPropertyException("tokenType"),
        false
    )
}

fun transform(value: AuthTable) = value.let {
    Auth(
        it.username,
        it.accessToken,
        it.refreshToken,
        it.tokenType,
        it.isActive
    )
}

fun transform(value: Auth) = value.let {
    AuthTable(
        it.username,
        it.accessToken,
        it.refreshToken,
        it.tokenType,
        it.isActive
    )
}
