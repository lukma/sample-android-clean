package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.cloud.AuthApi
import com.lukma.clean.data.auth.local.AuthDao
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.auth.entity.ThirdParty
import com.lukma.clean.extensions.runAsync

class AuthDataRepository(private val dao: AuthDao, private val api: AuthApi) : AuthRepository {
    override suspend fun authorize(usernameOrEmail: String, password: String) = runAsync {
        val auth = api.authorize(usernameOrEmail, password).await().let(AuthMapper::transform)
        dao.gets().map {
            if (it.username == usernameOrEmail) dao.delete(it)
            else dao.update(it.copy(isActive = false))
        }
        dao.insert(auth.copy(username = usernameOrEmail))
        Unit
    }

    override suspend fun authorize(thirdParty: ThirdParty, token: String) = runAsync {
        val auth = api.authorize(thirdParty.name, token).await().let(AuthMapper::transform)
        dao.gets().map {
            if (it.username == token) dao.delete(it)
            else dao.update(it.copy(isActive = false))
        }
        dao.insert(auth.copy(username = token))
        Unit
    }

    override suspend fun refreshToken() = runAsync {
        val auth = dao.getIsActive()
        api.refreshToken(auth.refreshToken).await()
            .let(AuthMapper::transform)
            .also { dao.update(it) }
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

    override suspend fun getAuthIsActive() = runAsync { dao.getIsActive() }

    override suspend fun isAuthenticated() = runAsync { dao.count() > 0 }

    override suspend fun logout() = runAsync {
        val auth = dao.getIsActive()
        dao.delete(auth)
        Unit
    }
}
