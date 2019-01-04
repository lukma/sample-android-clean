package com.lukma.clean.data.auth

import com.lukma.clean.data.common.helper.RepositoryHelper.runAsyncIO
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.auth.entity.ThirdParty

class AuthDataRepository(private val dao: AuthDao, private val api: AuthApi) : AuthRepository {
    override fun authorize(usernameOrEmail: String, password: String) = runAsyncIO {
        val loginResponse = api.authorize(usernameOrEmail, password).await().let(AuthMapper::transform)
        dao.gets().map {
            if (it.username == usernameOrEmail) dao.delete(it)
            else dao.update(it.copy(isActive = false))
        }
        dao.insert(loginResponse.copy(username = usernameOrEmail))
        Unit
    }

    override fun authorize(thirdParty: ThirdParty, token: String) = runAsyncIO {
        val loginResponse = api.authorize(thirdParty.name, token).await().let(AuthMapper::transform)
        dao.gets().map {
            if (it.username == token) dao.delete(it)
            else dao.update(it.copy(isActive = false))
        }
        dao.insert(loginResponse.copy(username = token))
        Unit
    }

    override fun refreshToken() = runAsyncIO {
        val auth = dao.getIsActive()
        val newAuth = api.refreshToken(auth.refreshToken).await().let(AuthMapper::transform)
        dao.update(newAuth)
        newAuth
    }

    override fun register(username: String, password: String, fullName: String, email: String) = runAsyncIO {
        api.register(username, password, fullName, email).await()
        Unit
    }

    override fun getAuthIsActive() = runAsyncIO { dao.getIsActive() }

    override fun isAuthenticated() = runAsyncIO { dao.count() > 0 }

    override fun logout() = runAsyncIO {
        val auth = dao.getIsActive()
        dao.delete(auth)
        Unit
    }
}
