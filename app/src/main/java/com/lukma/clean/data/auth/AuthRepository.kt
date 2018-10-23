package com.lukma.clean.data.auth

import io.reactivex.Flowable

interface AuthRepository {
    fun login(faId: String, fcmId: String): Flowable<Auth>

    fun register(
            faId: String,
            fcmId: String,
            facebookToken: String,
            googleToken: String
    ): Flowable<String>

    fun refreshToken(token: String): Flowable<Auth>

    fun isAuthenticated(): Flowable<Boolean>

    fun gets(): Flowable<List<Auth>>

    fun getIsActive(): Flowable<Auth>

    fun insert(data: Auth): Flowable<Boolean>

    fun update(data: Auth): Flowable<Boolean>

    fun delete(username: String): Flowable<Boolean>
}