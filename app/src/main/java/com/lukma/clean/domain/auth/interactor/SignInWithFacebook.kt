package com.lukma.clean.domain.auth.interactor

import com.google.firebase.auth.AuthResult
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class SignInWithFacebook(private val repository: AuthRepository)
    : UseCase<AuthResult, SignInWithFacebook.Params?>() {
    override fun build(params: Params?) = repository.signInWithFacebook(params?.token.orEmpty())

    data class Params(val token: String)
}