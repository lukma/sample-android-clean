package com.lukma.clean.data.auth.interactor

import com.google.firebase.auth.AuthResult
import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class SignInWithFacebook(private val repository: AuthRepository)
    : UseCase<AuthResult, SignInWithFacebook.Params?>() {
    override fun build(params: Params?) = repository.signInWithFacebook(params?.token.orEmpty())

    data class Params(val token: String)
}