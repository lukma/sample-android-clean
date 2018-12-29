package com.lukma.clean.domain.auth

import com.lukma.clean.domain.common.entity.ThirdParty
import kotlinx.coroutines.Deferred

interface AuthRepository {
    fun authorize(usernameOrEmail: String, password: String): Deferred<Unit>

    fun authorize(thirdParty: ThirdParty, token: String): Deferred<Unit>

    fun refreshToken(): Deferred<Auth>

    fun register(username: String, password: String, fullName: String, email: String): Deferred<Unit>

    fun getAuthIsActive(): Deferred<Auth>

    fun isAuthenticated(): Deferred<Boolean>

    fun logout(): Deferred<Unit>
}