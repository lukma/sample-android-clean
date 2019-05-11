package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.base.BaseDeferredUseCase

class LogoutUseCase(private val repository: AuthRepository) : BaseDeferredUseCase<Unit>() {
    override fun build(params: Map<String, Any?>) = repository.logout()
}
