package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class Logout(private val repository: AuthRepository) : UseCase<Unit>() {
    override fun build(params: Map<String, Any?>) = repository.logout()
}