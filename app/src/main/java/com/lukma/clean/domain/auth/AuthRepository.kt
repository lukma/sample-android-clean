package com.lukma.clean.domain.auth

import com.lukma.clean.data.auth.entity.table.AuthTable
import com.lukma.clean.domain.auth.entity.ThirdParty
import kotlinx.coroutines.Deferred

interface AuthRepository {
    fun authorize(usernameOrEmail: String, password: String): Deferred<Unit>

    fun authorize(thirdParty: ThirdParty, token: String): Deferred<Unit>

    fun refreshToken(): Deferred<AuthTable>

    fun register(username: String, password: String, fullName: String, email: String): Deferred<Unit>

    fun getAuthIsActive(): Deferred<AuthTable>

    fun isAuthenticated(): Deferred<Boolean>

    fun logout(): Deferred<Unit>
}
