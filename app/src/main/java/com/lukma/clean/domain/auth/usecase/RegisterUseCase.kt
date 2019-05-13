package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.common.base.BaseDeferredUseCase

class RegisterUseCase(private val repository: AuthRepository) : BaseDeferredUseCase<Unit>() {
    override suspend fun build(params: Map<String, Any?>) = repository.register(
        params[UseCaseConstant.USERNAME] as String,
        params[UseCaseConstant.PASSWORD] as String,
        params[UseCaseConstant.FULLNAME] as String,
        params[UseCaseConstant.EMAIL] as String
    )
}
