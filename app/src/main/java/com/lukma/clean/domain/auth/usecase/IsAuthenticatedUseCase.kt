package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.base.BaseDeferredUseCase

class IsAuthenticatedUseCase(private val repository: AuthRepository) :
    BaseDeferredUseCase<Boolean>() {

    override fun build(params: Map<String, Any?>) = repository.isAuthenticated()
}
