package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.store.AuthStoreFactory
import com.lukma.clean.data.common.DataStoreType
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.ThirdParty

class AuthDataRepository(private val store: AuthStoreFactory) : AuthRepository {
    override fun authorize(
        usernameOrEmail: String,
        password: String
    ) = store.createData().authorize(usernameOrEmail, password)

    override fun authorize(
        thirdParty: ThirdParty,
        token: String
    ) = store.createData().authorize(thirdParty, token)

    override fun register(
        username: String,
        password: String,
        fullName: String,
        email: String
    ) = store.createData().register(username, password, fullName, email)

    override fun refreshToken(token: String) = store.createData().refreshToken(token)

    override fun isAuthenticated() = store.createData(DataStoreType.DATABASE).isAuthenticated()

    override fun gets() = store.createData(DataStoreType.DATABASE).gets()

    override fun getIsActive() = store.createData(DataStoreType.DATABASE).getIsActive()

    override fun insert(data: Auth) = store.createData(DataStoreType.DATABASE).insert(data)

    override fun update(data: Auth) = store.createData(DataStoreType.DATABASE).update(data)

    override fun delete(username: String) = store.createData(DataStoreType.DATABASE).delete(username)
}