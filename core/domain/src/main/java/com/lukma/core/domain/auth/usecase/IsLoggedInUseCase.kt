package com.lukma.core.domain.auth.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class IsLoggedInUseCase(private val authRepository: AuthRepository) : BaseUseCase<Boolean>() {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    override suspend fun build() = authRepository.isLoggedIn()
}
