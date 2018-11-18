package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.store.AuthStoreFactory
import com.lukma.clean.data.common.DataStoreType
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.auth.AuthRepository

class AuthDataRepository(private val store: AuthStoreFactory) : AuthRepository {
    override fun signInWithEmail(email: String, password: String) = store
            .createData(DataStoreType.FIREBASE)
            .signInWithEmail(email, password)

    override fun signInWithFacebook(token: String) = store
            .createData(DataStoreType.FIREBASE)
            .signInWithFacebook(token)

    override fun signInWithGoogle(token: String) = store
            .createData(DataStoreType.FIREBASE)
            .signInWithGoogle(token)

    override fun createUserWithEmailAndPassword(email: String, password: String) = store
            .createData(DataStoreType.FIREBASE)
            .createUserWithEmailAndPassword(email, password)

    override fun updateProfile(fullName: String) = store
            .createData(DataStoreType.FIREBASE)
            .updateProfile(fullName)

    override fun authorize(faId: String, fcmId: String) = store
            .createData()
            .authorize(faId, fcmId)

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