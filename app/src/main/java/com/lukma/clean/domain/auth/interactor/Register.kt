package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class Register(private val repository: AuthRepository) : UseCase<String, Register.Params?>() {
    override fun build(params: Params?) = repository.register(
            params?.faId.orEmpty(),
            params?.fcmId.orEmpty(),
            params?.facebookToken.orEmpty(),
            params?.googleToken.orEmpty()
    ).map { params?.faId.orEmpty() }

    data class Params(
            val faId: String,
            val fcmId: String,
            val facebookToken: String = "",
            val googleToken: String = ""
    )
}