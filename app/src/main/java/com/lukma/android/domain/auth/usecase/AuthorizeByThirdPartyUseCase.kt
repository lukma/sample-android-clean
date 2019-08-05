package com.lukma.android.domain.auth.usecase

import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.auth.entity.ThirdParty
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.common.base.BaseUseCase

class AuthorizeByThirdPartyUseCase(private val repository: AuthRepository) : BaseUseCase<Unit>() {
    override suspend fun build(params: Map<String, Any?>) = repository.authorize(
        params[UseCaseConstant.THIRD_PARTY] as ThirdParty,
        params[UseCaseConstant.TOKEN] as String
    )
}
