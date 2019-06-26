package com.lukma.clean.domain.preference.usecase

import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.common.base.BaseUseCase
import com.lukma.clean.domain.preference.PreferenceRepository

class SaveFcmUseCase(private val repository: PreferenceRepository) : BaseUseCase<Unit>() {
    override suspend fun build(params: Map<String, Any?>) = repository.saveFcmId(
        params[UseCaseConstant.TOKEN] as String
    )
}
