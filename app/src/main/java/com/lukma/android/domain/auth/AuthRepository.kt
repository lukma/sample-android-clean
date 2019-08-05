package com.lukma.android.domain.auth

import com.lukma.android.domain.auth.entity.Auth
import com.lukma.android.domain.auth.entity.ThirdParty

interface AuthRepository {
    suspend fun authorize(usernameOrEmail: String, password: String)

    suspend fun authorize(thirdParty: ThirdParty, token: String)

    suspend fun refreshToken(): Auth

    suspend fun register(username: String, password: String, fullName: String, email: String)

    suspend fun getAuthIsActive(): Auth

    suspend fun isAuthenticated(): Boolean

    suspend fun logout()
}
