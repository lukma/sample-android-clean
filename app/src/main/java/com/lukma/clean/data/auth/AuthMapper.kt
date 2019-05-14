package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.response.AuthResponse
import com.lukma.clean.data.auth.local.AuthTable
import org.koin.core.error.MissingPropertyException

fun transform(value: AuthResponse) = value.let {
    AuthTable(
        String(),
        it.token?.accessToken ?: throw MissingPropertyException("accessToken"),
        it.token.refreshToken ?: throw MissingPropertyException("refreshToken"),
        it.token.tokenType ?: throw MissingPropertyException("tokenType")
    )
}
