package com.lukma.android.domain.preference.usecase

import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.common.base.BaseUseCase
import com.lukma.android.domain.preference.PreferenceRepository

class SaveFcmUseCase(private val repository: PreferenceRepository) : BaseUseCase<Unit>() {
    override suspend fun build(params: Map<String, Any?>) = repository.saveFcmId(
        params[UseCaseConstant.TOKEN] as String
    )
}
