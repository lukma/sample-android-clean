package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class Logout(private val repository: AuthRepository) : UseCase<Boolean, Void?>() {
    override fun build(params: Void?) = repository
            .getIsActive()
            .flatMap { repository.delete(it.username) }
}