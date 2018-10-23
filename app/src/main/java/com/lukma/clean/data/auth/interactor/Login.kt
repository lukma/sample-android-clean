package com.lukma.clean.data.auth.interactor

import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class Login(private val repository: AuthRepository) : UseCase<Boolean, Login.Params?>() {
    override fun build(params: Params?) = repository
            .login(params?.faId.orEmpty(), params?.fcmID.orEmpty())
            .doOnNext { _ ->
                repository.gets().map { result ->
                    result.map {
                        if (it.username != params?.faId) {
                            repository.update(it.copy(isActive = false))
                        } else {
                            repository.delete(it.username)
                        }
                    }
                }
            }
            .flatMap { repository.insert(it.copy(username = params?.faId.orEmpty())) }

    data class Params(val faId: String, val fcmID: String)
}