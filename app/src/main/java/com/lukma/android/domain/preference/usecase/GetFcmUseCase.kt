package com.lukma.android.domain.preference.usecase

import com.lukma.android.domain.common.base.BaseUseCase
import com.lukma.android.domain.preference.PreferenceRepository

class GetFcmUseCase(private val repository: PreferenceRepository) : BaseUseCase<String>() {
    override suspend fun build(params: Map<String, Any?>) = repository.getFcmId()
}
