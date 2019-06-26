package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.base.BaseUseCase

class IsAuthenticatedUseCase(private val repository: AuthRepository) : BaseUseCase<Boolean>() {
    override suspend fun build(params: Map<String, Any?>) = repository.isAuthenticated()
}
