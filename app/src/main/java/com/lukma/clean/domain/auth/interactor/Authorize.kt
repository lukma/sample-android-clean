package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class Authorize(private val repository: AuthRepository) : UseCase<Boolean, Authorize.Params?>() {
    override fun build(params: Params?) = repository
            .authorize(params?.faId.orEmpty(), params?.fcmId.orEmpty())
            .doOnNext { _ ->
                repository.gets().map {
                    it.map { auth ->
                        if (auth.username == params?.faId.orEmpty()) {
                            repository.delete(auth.username)
                        } else {
                            repository.update(auth.copy(isActive = false))
                        }
                    }
                }
            }
            .flatMap {
                repository.insert(it.copy(username = params?.faId.orEmpty()))
            }

    data class Params(val faId: String, val fcmId: String)
}