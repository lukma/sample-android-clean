package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class Register(private val repository: AuthRepository) : UseCase<Boolean, Register.Params?>() {
    override fun build(params: Params?) = repository.register(
        params?.username.orEmpty(),
        params?.password.orEmpty(),
        params?.fullName.orEmpty(),
        params?.email.orEmpty()
    )

    data class Params(
        val username: String,
        val password: String,
        val fullName: String,
        val email: String
    )
}