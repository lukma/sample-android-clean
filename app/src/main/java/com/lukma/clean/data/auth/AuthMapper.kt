package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.response.AuthResponse
import com.lukma.clean.data.auth.local.AuthTable
import com.lukma.clean.domain.auth.entity.Auth

fun transform(value: AuthResponse) = value.let {
    Auth(
        String(),
        it.token?.accessToken ?: throw NoSuchElementException("accessToken"),
        it.token.refreshToken ?: throw NoSuchElementException("refreshToken"),
        it.token.tokenType ?: throw NoSuchElementException("tokenType"),
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
