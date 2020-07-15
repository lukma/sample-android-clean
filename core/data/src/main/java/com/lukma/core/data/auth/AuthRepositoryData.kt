package com.lukma.core.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.lukma.core.domain.auth.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryData(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    override suspend fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    override suspend fun signInWithEmail(email: String, password: String) {
        runCatching {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
        }.getOrThrow()
    }

    override suspend fun signUpWithEmail(email: String, password: String, displayName: String) {
        runCatching {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val request = UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build()
            firebaseAuth.currentUser?.updateProfile(request)?.await()
        }.getOrThrow()
    }
}
