package com.lukma.clean.data.auth.interactor

import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class Logout(private val repository: AuthRepository) : UseCase<Boolean, Void?>() {
    override fun build(params: Void?) = repository
            .getIsActive()
            .flatMap { repository.delete(it.username) }
}