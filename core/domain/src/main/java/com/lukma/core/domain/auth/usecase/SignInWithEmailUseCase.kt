package com.lukma.core.domain.auth.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class SignInWithEmailUseCase(private val authRepository: AuthRepository) : BaseUseCase<Unit>() {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun addParams(email: String, password: String) = apply {
        val params = mapOf(
            KEY_EMAIL to email,
            KEY_PASSWORD to password
        )
        addParams(params)
    }

    override suspend fun build() = authRepository.signInWithEmail(
        params[KEY_EMAIL] as String,
        params[KEY_PASSWORD] as String
    )

    companion object {
        private const val KEY_EMAIL = "KEY_EMAIL"
        private const val KEY_PASSWORD = "KEY_PASSWORD"
    }
}
