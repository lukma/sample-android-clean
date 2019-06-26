package com.lukma.clean.domain.preference.usecase

import com.lukma.clean.domain.common.base.BaseUseCase
import com.lukma.clean.domain.preference.PreferenceRepository

class GetFcmUseCase(private val repository: PreferenceRepository) : BaseUseCase<String>() {
    override suspend fun build(params: Map<String, Any?>) = repository.getFcmId()
}
