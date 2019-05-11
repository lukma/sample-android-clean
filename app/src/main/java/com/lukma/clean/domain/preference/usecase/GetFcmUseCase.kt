package com.lukma.clean.domain.preference.usecase

import com.lukma.clean.domain.common.base.BaseDeferredUseCase
import com.lukma.clean.domain.preference.PreferenceRepository

class GetFcmUseCase(private val repository: PreferenceRepository) : BaseDeferredUseCase<String>() {
    override fun build(params: Map<String, Any?>) = repository.getFcmId()
}
