package com.lukma.core.domain.auth

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signUpWithEmail(email: String, password: String, displayName: String)
}
