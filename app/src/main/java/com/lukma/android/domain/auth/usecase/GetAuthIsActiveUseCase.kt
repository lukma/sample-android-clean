package com.lukma.android.domain.auth.usecase

import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.auth.entity.Auth
import com.lukma.android.domain.common.base.BaseUseCase

class GetAuthIsActiveUseCase(private val repository: AuthRepository) : BaseUseCase<Auth>() {
    override suspend fun build() = repository.getAuthIsActive()
}
