package com.lukma.clean.data.auth.interactor

import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

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