package com.lukma.clean.domain.preference.interactor

import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.preference.PreferenceRepository

class SaveFcmUseCase(private val repository: PreferenceRepository) : UseCase<Unit>() {
    override fun build(params: Map<String, Any?>) = repository.saveFcmId(
        params[UseCaseConstant.TOKEN] as String
    )
}