package com.lukma.core.domain.account.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.account.AccountRepository
import com.lukma.core.domain.account.Profile
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class GetProfileUseCase(private val accountRepository: AccountRepository) : BaseUseCase<Profile>() {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    override suspend fun build() = accountRepository.getProfile()
}
