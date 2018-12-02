package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class AuthorizeByUsernameOrEmail(
    private val repository: AuthRepository
) : UseCase<Boolean, AuthorizeByUsernameOrEmail.Params?>() {
    override fun build(params: Params?) = repository
        .authorize(params?.usernameOrEmail.orEmpty(), params?.password.orEmpty())
        .doOnNext { _ ->
            repository.gets().map { auths ->
                auths.map {
                    if (it.username == params?.usernameOrEmail) {
                        repository.delete(it.username)
                    } else {
                        repository.update(it.copy(isActive = false))
                    }
                }
            }
        }
        .flatMap { repository.insert(it.copy(username = params?.usernameOrEmail.orEmpty())) }


    data class Params(val usernameOrEmail: String, val password: String)
}