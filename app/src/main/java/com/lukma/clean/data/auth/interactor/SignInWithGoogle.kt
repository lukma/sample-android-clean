package com.lukma.clean.data.auth.interactor

import com.google.firebase.auth.AuthResult
import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class SignInWithGoogle(private val repository: AuthRepository)
    : UseCase<AuthResult, SignInWithGoogle.Params?>() {
    override fun build(params: Params?) = repository.signInWithGoogle(params?.token.orEmpty())

    data class Params(val token: String)
}