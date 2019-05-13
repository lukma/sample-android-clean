package com.lukma.clean.domain.auth

import com.lukma.clean.data.auth.local.AuthTable
import com.lukma.clean.domain.auth.entity.ThirdParty
import kotlinx.coroutines.Deferred

interface AuthRepository {
    suspend fun authorize(usernameOrEmail: String, password: String): Deferred<Unit>

    suspend fun authorize(thirdParty: ThirdParty, token: String): Deferred<Unit>

    suspend fun refreshToken(): Deferred<AuthTable>

    suspend fun register(
        username: String,
        password: String,
        fullName: String,
        email: String
    ): Deferred<Unit>

    suspend fun getAuthIsActive(): Deferred<AuthTable>

    suspend fun isAuthenticated(): Deferred<Boolean>

    suspend fun logout(): Deferred<Unit>
}
