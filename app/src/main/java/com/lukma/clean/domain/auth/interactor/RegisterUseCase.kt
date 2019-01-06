package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.common.UseCaseConstant

class RegisterUseCase(private val repository: AuthRepository) : UseCase<Unit>() {
    override fun build(params: Map<String, Any?>) = repository.register(
        params[UseCaseConstant.USERNAME] as String,
        params[UseCaseConstant.PASSWORD] as String,
        params[UseCaseConstant.FULLNAME] as String,
        params[UseCaseConstant.EMAIL] as String
    )
}