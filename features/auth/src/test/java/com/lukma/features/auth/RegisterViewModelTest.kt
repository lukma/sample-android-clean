package com.lukma.features.auth

import androidx.lifecycle.Observer
import com.lukma.core.domain.Either
import com.lukma.core.domain.EventState
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import com.lukma.core.domain.auth.usecase.SignUpWithEmailUseCase
import com.lukma.features.auth.register.RegisterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExtendWith(TaskExecutorExtension::class)
class RegisterViewModelTest : KoinTest {
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase = mockk()
    private val signInWithEmailUseCase: SignInWithEmailUseCase = mockk()
    private lateinit var viewModel: RegisterViewModel

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { signUpWithEmailUseCase }
                single { signInWithEmailUseCase }
            })
        }
        viewModel = RegisterViewModel()
    }

    @Nested
    inner class SignUpWithEmail {
        private val authResultObserver: Observer<EventState<Unit>> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.authResult.observeForever(authResultObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.authResult.removeObserver(authResultObserver)
        }

        @Test
        fun `given success process when sign in with email then notify observer with unit`() {
            // given
            val email = "dummy@mail.com"
            val password = "qwerty"
            val displayName = "dummy"
            coEvery {
                signUpWithEmailUseCase.addParams(any(), any(), any()).invoke()
            } returns Either.Value(Unit)
            coEvery {
                signInWithEmailUseCase.addParams(email, password).invoke()
            } returns Either.Value(Unit)

            // when
            runBlocking { viewModel.signUpWithEmail(email, password, displayName) }

            // then
            verifySequence {
                authResultObserver.onChanged(EventState.Loading)
                authResultObserver.onChanged(EventState.Success(Unit))
            }
        }

        @Test
        fun `given fail process when sign in with email then notify observer with error`() {
            // given
            val error = Exception("failed")
            coEvery {
                signUpWithEmailUseCase.addParams(any(), any(), any()).invoke()
            } returns Either.Error(error)

            // when
            runBlocking { viewModel.signUpWithEmail("dummy@mail.com", "qwerty", "dummy") }

            // then
            verifySequence {
                authResultObserver.onChanged(EventState.Loading)
                authResultObserver.onChanged(EventState.Failure(error))
            }
        }
    }
}
