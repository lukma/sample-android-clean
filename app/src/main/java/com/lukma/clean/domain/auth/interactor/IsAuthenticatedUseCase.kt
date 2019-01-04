package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class IsAuthenticatedUseCase(private val repository: AuthRepository) : UseCase<Boolean>() {
    override fun build(params: Map<String, Any?>) = repository.isAuthenticated()
}
