package com.lukma.clean.data.auth.store.cloud

import com.lukma.clean.data.auth.Auth
import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.auth.store.AuthMapper

class AuthCloudStore(private val api: AuthApi, private val mapper: AuthMapper) : AuthRepository {
    override fun signInWithEmail(email: String, password: String) = throw IllegalAccessException()

    override fun signInWithFacebook(token: String) = throw IllegalAccessException()

    override fun signInWithGoogle(token: String) = throw IllegalAccessException()

    override fun authorize(faId: String, fcmId: String) = api
            .authorize(faId, fcmId)
            .map(mapper::transform)

    override fun register(
            faId: String,
            fcmId: String,
            facebookToken: String,
            googleToken: String
    ) = api.register(faId, fcmId, facebookToken, googleToken).map { it.id }

    override fun refreshToken(token: String) = api
            .refreshToken(token)
            .map(mapper::transform)

    override fun isAuthenticated() = throw IllegalAccessException()

    override fun gets() = throw IllegalAccessException()

    override fun getIsActive() = throw IllegalAccessException()

    override fun insert(data: Auth) = throw IllegalAccessException()

    override fun update(data: Auth) = throw IllegalAccessException()

    override fun delete(username: String) = throw IllegalAccessException()
}