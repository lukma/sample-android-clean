package com.lukma.android.domain.auth.usecase

import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.common.base.BaseUseCase

class AuthorizeByUsernameOrEmailUseCase(private val repository: AuthRepository) :
    BaseUseCase<Unit>() {

    override suspend fun build(params: Map<String, Any?>) = repository.authorize(
        params[UseCaseConstant.USERNAME] as String,
        params[UseCaseConstant.PASSWORD] as String
    )
}
