package com.lukma.clean.data.auth.store.cloud

import com.lukma.clean.data.auth.store.AuthMapper
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.ThirdParty

class AuthCloudStore(private val api: AuthApi, private val mapper: AuthMapper) : AuthRepository {
    override fun authorize(usernameOrEmail: String, password: String) = api
        .authorize(usernameOrEmail, password)
        .map(mapper::transform)

    override fun authorize(thirdParty: ThirdParty, token: String) = api
        .authorize(thirdParty.name, token)
        .map(mapper::transform)

    override fun register(
        username: String,
        password: String,
        fullName: String,
        email: String
    ) = api.register(username, password, fullName, email).map { it.id.isNotEmpty() }

    override fun refreshToken(token: String) = api.refreshToken(token).map(mapper::transform)

    override fun isAuthenticated() = throw IllegalAccessException()

    override fun gets() = throw IllegalAccessException()

    override fun getIsActive() = throw IllegalAccessException()

    override fun insert(data: Auth) = throw IllegalAccessException()

    override fun update(data: Auth) = throw IllegalAccessException()

    override fun delete(username: String) = throw IllegalAccessException()
}