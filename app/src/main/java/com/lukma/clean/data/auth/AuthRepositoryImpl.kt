package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.store.AuthStoreFactory
import com.lukma.clean.data.common.DataStoreType

class AuthRepositoryImpl(private val store: AuthStoreFactory) : AuthRepository {
    override fun login(faId: String, fcmId: String) = store
            .createData()
            .login(faId, fcmId)

    override fun register(
            faId: String,
            fcmId: String,
            facebookToken: String,
            googleToken: String
    ) = store.createData().register(faId, fcmId, facebookToken, googleToken)

    override fun refreshToken(token: String) = store
            .createData()
            .refreshToken(token)

    override fun isAuthenticated() = store
            .createData(DataStoreType.DATABASE)
            .isAuthenticated()

    override fun gets() = store
            .createData(DataStoreType.DATABASE)
            .gets()

    override fun getIsActive() = store
            .createData(DataStoreType.DATABASE)
            .getIsActive()

    override fun insert(data: Auth) = store
            .createData(DataStoreType.DATABASE)
            .insert(data)

    override fun update(data: Auth) = store
            .createData(DataStoreType.DATABASE)
            .update(data)

    override fun delete(username: String) = store
            .createData(DataStoreType.DATABASE)
            .delete(username)
}