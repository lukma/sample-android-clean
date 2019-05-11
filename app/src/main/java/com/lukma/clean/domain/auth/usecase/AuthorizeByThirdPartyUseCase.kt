package com.lukma.clean.domain.auth.usecase

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.auth.entity.ThirdParty
import com.lukma.clean.domain.common.base.BaseDeferredUseCase
import com.lukma.clean.domain.common.UseCaseConstant

class AuthorizeByThirdPartyUseCase(private val repository: AuthRepository) :
    BaseDeferredUseCase<Unit>() {

    override fun build(params: Map<String, Any?>) = repository.authorize(
        params[UseCaseConstant.THIRD_PARTY] as ThirdParty,
        params[UseCaseConstant.TOKEN] as String
    )
}
