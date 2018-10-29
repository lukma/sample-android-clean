package com.lukma.clean.data.auth.interactor

import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class Register(private val repository: AuthRepository) : UseCase<String, Register.Params?>() {
    override fun build(params: Params?) = repository.register(
            params?.faId.orEmpty(),
            params?.fcmId.orEmpty(),
            params?.facebookToken.orEmpty(),
            params?.googleToken.orEmpty()
    )

    data class Params(
            val faId: String,
            val fcmId: String,
            val facebookToken: String = "",
            val googleToken: String = ""
    )
}