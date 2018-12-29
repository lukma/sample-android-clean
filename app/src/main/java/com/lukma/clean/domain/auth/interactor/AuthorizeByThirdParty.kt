package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.entity.ThirdParty
import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.common.UseCaseConstant

class AuthorizeByThirdParty(private val repository: AuthRepository) : UseCase<Unit>() {
    override fun build(params: Map<String, Any?>) = repository.authorize(
        params[UseCaseConstant.THIRD_PARTY] as ThirdParty,
        params[UseCaseConstant.TOKEN] as String
    )
}