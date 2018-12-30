package com.lukma.clean.domain.preference.interactor

import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.preference.PreferenceRepository

class GetFcmUseCase(private val repository: PreferenceRepository) : UseCase<String>() {
    override fun build(params: Map<String, Any?>) = repository.getFcmId()
}