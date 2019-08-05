package com.lukma.android.data.auth

import com.lukma.android.data.auth.cloud.AuthApi
import com.lukma.android.data.auth.local.AuthDao
import com.lukma.android.data.common.exception.repo.NotFoundException
import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.auth.entity.Auth
import com.lukma.android.domain.auth.entity.ThirdParty

class AuthDataRepository(private val dao: AuthDao, private val api: AuthApi) : AuthRepository {
    override suspend fun authorize(usernameOrEmail: String, password: String) {
        dao.gets().forEach {
            if (it.username != usernameOrEmail) {
                dao.update(it.copy(isActive = false))
            } else {
                dao.delete(it)
            }
        }
        val auth = api.authorize(usernameOrEmail, password)
            .let(::transform)
            .copy(username = usernameOrEmail)
            .let(::transform)
        dao.insert(auth)
    }

    override suspend fun authorize(thirdParty: ThirdParty, token: String) {
        dao.gets().forEach {
            if (it.username != token) {
                dao.update(it.copy(isActive = false))
            } else {
                dao.delete(it)
            }
        }
        val auth = api.authorize(thirdParty.name, token)
            .let(::transform)
            .copy(username = token)
            .let(::transform)
        dao.insert(auth)
    }

    override suspend fun refreshToken(): Auth {
        val auth = dao.getIsActive() ?: throw NotFoundException()
        return api.refreshToken(auth.refreshToken).let(::transform).also {
            val newAuth = it.copy(username = auth.username).let(::transform)
            dao.update(newAuth)
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        fullName: String,
        email: String
    ) {
        api.register(username, password, fullName, email)
    }

    override suspend fun getAuthIsActive(): Auth =
        dao.getIsActive()?.let(::transform) ?: throw NotFoundException()

    override suspend fun isAuthenticated(): Boolean =
        dao.getIsActive() != null && dao.count() > 0

    override suspend fun logout() {
        val auth = dao.getIsActive() ?: throw NotFoundException()
        dao.delete(auth)
    }
}
