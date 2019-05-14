package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.AuthApi
import com.lukma.clean.data.auth.local.AuthDao
import com.lukma.clean.data.common.exception.repo.NotFoundException
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.auth.entity.ThirdParty
import com.lukma.clean.extensions.runAsync

class AuthDataRepository(private val dao: AuthDao, private val api: AuthApi) : AuthRepository {
    override suspend fun authorize(usernameOrEmail: String, password: String) = runAsync {
        val auth = api.authorize(usernameOrEmail, password).await().let(::transform)
        dao.getIsActive()
            ?.let { dao.update(it.copy(isActive = false)) }
            ?: dao.gets().find { it.username == usernameOrEmail }?.let { dao.delete(it) }
        dao.insert(auth.copy(username = usernameOrEmail).let(::transform))
        Unit
    }

    override suspend fun authorize(thirdParty: ThirdParty, token: String) = runAsync {
        val auth = api.authorize(thirdParty.name, token).await().let(::transform)
        dao.getIsActive()
            ?.let { dao.update(it.copy(isActive = false)) }
            ?: dao.gets().find { it.username == token }?.let { dao.delete(it) }
        dao.insert(auth.copy(username = token).let(::transform))
        Unit
    }

    override suspend fun refreshToken() = runAsync {
        val auth = dao.getIsActive() ?: throw NotFoundException()
        api.refreshToken(auth.refreshToken).await()
            .let(::transform)
            .also { dao.update(it.let(::transform)) }
    }

    override suspend fun register(
        username: String,
        password: String,
        fullName: String,
        email: String
    ) = runAsync {
        api.register(username, password, fullName, email).await()
        Unit
    }

    override suspend fun getAuthIsActive() = runAsync {
        dao.getIsActive()?.let(::transform) ?: throw NotFoundException()
    }

    override suspend fun isAuthenticated() = runAsync { dao.count() > 0 }

    override suspend fun logout() = runAsync {
        val auth = dao.getIsActive() ?: throw NotFoundException()
        dao.delete(auth)
        Unit
    }
}
