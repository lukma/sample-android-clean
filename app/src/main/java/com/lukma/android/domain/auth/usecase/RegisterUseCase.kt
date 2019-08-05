package com.lukma.android.domain.auth.usecase

import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.common.base.BaseUseCase

class RegisterUseCase(private val repository: AuthRepository) : BaseUseCase<Unit>() {
    override suspend fun build(params: Map<String, Any?>) = repository.register(
        params[UseCaseConstant.USERNAME] as String,
        params[UseCaseConstant.PASSWORD] as String,
        params[UseCaseConstant.FULLNAME] as String,
        params[UseCaseConstant.EMAIL] as String
    )
}
