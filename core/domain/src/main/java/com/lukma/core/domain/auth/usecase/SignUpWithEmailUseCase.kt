package com.lukma.core.domain.auth.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class SignUpWithEmailUseCase(private val authRepository: AuthRepository) : BaseUseCase<Unit>() {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun addParams(email: String, password: String, displayName: String) = apply {
        val params = mapOf(
            KEY_EMAIL to email,
            KEY_PASSWORD to password,
            KEY_DISPLAY_NAME to displayName
        )
        addParams(params)
    }

    override suspend fun build() = authRepository.signUpWithEmail(
        params[KEY_EMAIL] as String,
        params[KEY_PASSWORD] as String,
        params[KEY_DISPLAY_NAME] as String
    )

    companion object {
        private const val KEY_EMAIL = "KEY_EMAIL"
        private const val KEY_PASSWORD = "KEY_PASSWORD"
        private const val KEY_DISPLAY_NAME = "KEY_DISPLAY_NAME"
    }
}
