package com.lukma.clean.domain.auth

import com.lukma.clean.domain.common.ThirdParty
import io.reactivex.Flowable

interface AuthRepository {
    fun authorize(usernameOrEmail: String, password: String): Flowable<Auth>

    fun authorize(thirdParty: ThirdParty, token: String): Flowable<Auth>

    fun register(username: String, password: String, fullName: String, email: String): Flowable<Boolean>

    fun refreshToken(token: String): Flowable<Auth>

    fun isAuthenticated(): Flowable<Boolean>

    fun gets(): Flowable<List<Auth>>

    fun getIsActive(): Flowable<Auth>

    fun insert(data: Auth): Flowable<Boolean>

    fun update(data: Auth): Flowable<Boolean>

    fun delete(username: String): Flowable<Boolean>
}