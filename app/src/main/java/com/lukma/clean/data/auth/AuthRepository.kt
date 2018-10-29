package com.lukma.clean.data.auth

import com.google.firebase.auth.AuthResult
import io.reactivex.Flowable

interface AuthRepository {
    fun signInWithEmail(email: String, password: String): Flowable<AuthResult>

    fun signInWithFacebook(token: String): Flowable<AuthResult>

    fun signInWithGoogle(token: String): Flowable<AuthResult>

    fun authorize(faId: String, fcmId: String): Flowable<Auth>

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