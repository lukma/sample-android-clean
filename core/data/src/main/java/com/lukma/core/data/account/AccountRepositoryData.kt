package com.lukma.core.data.account

import com.google.firebase.auth.FirebaseAuth
import com.lukma.core.data.common.NotLoggedInException
import com.lukma.core.domain.account.AccountRepository
import com.lukma.core.domain.account.Profile

class AccountRepositoryData(private val firebaseAuth: FirebaseAuth) : AccountRepository {
    override suspend fun getProfile(): Profile {
        val email = firebaseAuth.currentUser?.email ?: throw NotLoggedInException()
        val displayName = firebaseAuth.currentUser?.displayName ?: ""
        return Profile(email, displayName)
    }
}
