package com.lukma.clean.data.auth.interactor

import com.google.firebase.auth.AuthResult
import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class SignInWithEmail(private val repository: AuthRepository)
    : UseCase<AuthResult, SignInWithEmail.Params?>() {
    override fun build(params: Params?) = repository
            .signInWithEmail(params?.email.orEmpty(), params?.password.orEmpty())

    data class Params(val email: String, val password: String)
}