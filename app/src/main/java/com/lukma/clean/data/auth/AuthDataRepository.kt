package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.AuthApi
import com.lukma.clean.data.auth.local.AuthDao
import com.lukma.clean.data.common.exception.repo.NotFoundException
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.auth.entity.Auth
import com.lukma.clean.domain.auth.entity.ThirdParty

class AuthDataRepository(private val dao: AuthDao, private val api: AuthApi) : AuthRepository {
    override suspend fun authorize(usernameOrEmail: String, password: String) {
        val auth = api.authorize(usernameOrEmail, password).let(::transform)
        dao.getIsActive()
            ?.let { dao.update(it.copy(isActive = false)) }
            ?: dao.gets().find { it.username == usernameOrEmail }?.let { dao.delete(it) }
        dao.insert(auth.copy(username = usernameOrEmail).let(::transform))
    }

    override suspend fun authorize(thirdParty: ThirdParty, token: String) {
        val auth = api.authorize(thirdParty.name, token).let(::transform)
        dao.getIsActive()
            ?.let { dao.update(it.copy(isActive = false)) }
            ?: dao.gets().find { it.username == token }?.let { dao.delete(it) }
        dao.insert(auth.copy(username = token).let(::transform))
    }

    override suspend fun refreshToken(): Auth {
        val auth = dao.getIsActive() ?: throw NotFoundException()
        return api.refreshToken(auth.refreshToken)
            .let(::transform)
            .also {
                val newAuth =
                    it.let(::transform).copy(username = auth.username, isActive = auth.isActive)
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

    override suspend fun getAuthIsActive() =
        dao.getIsActive()?.let(::transform) ?: throw NotFoundException()

    override suspend fun isAuthenticated() = dao.count() > 0

    override suspend fun logout() {
        val auth = dao.getIsActive() ?: throw NotFoundException()
        dao.delete(auth)
    }
}
