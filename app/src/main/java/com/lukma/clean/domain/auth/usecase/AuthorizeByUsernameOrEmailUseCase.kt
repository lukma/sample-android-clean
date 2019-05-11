package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.base.BaseDeferredUseCase
import com.lukma.clean.domain.common.UseCaseConstant

class AuthorizeByUsernameOrEmailUseCase(private val repository: AuthRepository) :
    BaseDeferredUseCase<Unit>() {

    override fun build(params: Map<String, Any?>) = repository.authorize(
        params[UseCaseConstant.USERNAME] as String,
        params[UseCaseConstant.PASSWORD] as String
    )
}
