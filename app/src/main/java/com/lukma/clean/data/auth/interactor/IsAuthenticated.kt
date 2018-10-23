package com.lukma.clean.data.auth.interactor

import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.common.UseCase

class IsAuthenticated(private val repository: AuthRepository) : UseCase<Boolean, Void?>() {
    override fun build(params: Void?) = repository.isAuthenticated()
}