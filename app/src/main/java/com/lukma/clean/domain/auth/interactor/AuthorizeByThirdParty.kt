package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.ThirdParty
import com.lukma.clean.domain.common.UseCase

class AuthorizeByThirdParty(
    private val repository: AuthRepository
) : UseCase<Boolean, AuthorizeByThirdParty.Params?>() {
    override fun build(params: Params?) = repository
        .authorize(params?.thirdParty ?: ThirdParty.UNKNOWN, params?.token.orEmpty())
        .doOnNext { _ ->
            repository.gets().map { auths ->
                auths.map {
                    if (it.username == params?.token) {
                        repository.delete(it.username)
                    } else {
                        repository.update(it.copy(isActive = false))
                    }
                }
            }
        }
        .flatMap { repository.insert(it.copy(username = params?.token.orEmpty())) }


    data class Params(val thirdParty: ThirdParty, val token: String)
}