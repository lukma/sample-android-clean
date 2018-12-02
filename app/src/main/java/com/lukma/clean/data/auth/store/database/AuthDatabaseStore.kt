package com.lukma.clean.data.auth.store.database

import com.lukma.clean.data.auth.store.AuthMapper
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.ThirdParty
import io.reactivex.Flowable

class AuthDatabaseStore(private val dao: AuthDao, private val mapper: AuthMapper) : AuthRepository {
    override fun authorize(
        usernameOrEmail: String,
        password: String
    ) = throw IllegalAccessException()

    override fun authorize(
        thirdParty: ThirdParty,
        token: String
    ) = throw IllegalAccessException()

    override fun register(
        username: String,
        password: String,
        fullName: String,
        email: String
    ) = throw IllegalAccessException()

    override fun refreshToken(token: String) = throw IllegalAccessException()

    override fun isAuthenticated() = dao.count().map { it > 0 }

    override fun gets() = dao.gets().map(mapper::transform)

    override fun getIsActive() = dao.getIsActive().map(mapper::transform)

    override fun insert(data: Auth) = Flowable
        .just(dao.insert(mapper.transform(data)))
        .map { it.toInt() == 1 }

    override fun update(data: Auth) = Flowable
        .just(dao.update(mapper.transform(data)))
        .map { it == 1 }

    override fun delete(username: String) = dao.get(username).map { dao.delete(it) }.map { it == 1 }
}