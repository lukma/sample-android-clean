package com.lukma.android.domain.auth.usecase

import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.common.base.BaseUseCase

class IsAuthenticatedUseCase(private val repository: AuthRepository) : BaseUseCase<Boolean>() {
    override suspend fun build() = repository.isAuthenticated()
}
