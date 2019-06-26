package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.base.BaseUseCase

class LogoutUseCase(private val repository: AuthRepository) : BaseUseCase<Unit>() {
    override suspend fun build(params: Map<String, Any?>) = repository.logout()
}
