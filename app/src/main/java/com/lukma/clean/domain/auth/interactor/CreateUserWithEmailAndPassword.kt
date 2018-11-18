package com.lukma.clean.domain.auth.interactor

import com.google.firebase.auth.AuthResult
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class CreateUserWithEmailAndPassword(private val repository: AuthRepository)
    : UseCase<AuthResult, CreateUserWithEmailAndPassword.Params?>() {
    override fun build(params: Params?) = repository
            .createUserWithEmailAndPassword(params?.email.orEmpty(), params?.password.orEmpty())

    data class Params(val email: String, val password: String)
}