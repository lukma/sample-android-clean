package com.lukma.clean.domain.auth.interactor

import com.google.firebase.auth.AuthResult
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class SignInWithGoogle(private val repository: AuthRepository)
    : UseCase<AuthResult, SignInWithGoogle.Params?>() {
    override fun build(params: Params?) = repository.signInWithGoogle(params?.token.orEmpty())

    data class Params(val token: String)
}