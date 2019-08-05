package com.lukma.android.data.auth

import com.lukma.android.data.auth.cloud.response.AuthResponse
import com.lukma.android.data.auth.local.AuthTable
import com.lukma.android.domain.auth.entity.Auth

fun transform(value: AuthResponse) = Auth(
    String(),
    value.token?.accessToken ?: throw NoSuchElementException("accessToken"),
    value.token.refreshToken ?: throw NoSuchElementException("refreshToken"),
    value.token.tokenType ?: throw NoSuchElementException("tokenType"),
    false
)

fun transform(value: AuthTable) = Auth(
    value.username,
    value.accessToken,
    value.refreshToken,
    value.tokenType,
    value.isActive
)

fun transform(value: Auth) = AuthTable(
    value.username,
    value.accessToken,
    value.refreshToken,
    value.tokenType,
    value.isActive
)
