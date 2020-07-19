package com.lukma.features.auth

import androidx.lifecycle.Observer
import com.lukma.core.domain.Either
import com.lukma.core.domain.EventState
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import com.lukma.core.test.TaskExecutorExtension
import com.lukma.features.auth.login.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@ExtendWith(TaskExecutorExtension::class)
class LoginViewModelTest : KoinTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private val signInWithEmailUseCase: SignInWithEmailUseCase = mockk()
    private lateinit var viewModel: LoginViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        stopKoin()
        startKoin {
            modules(module {
                single { signInWithEmailUseCase }
            })
        }
        viewModel = LoginViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Nested
    inner class SignInWithEmail {
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
            coEvery {
                signInWithEmailUseCase.addParams(email, password).invoke()
            } returns Either.Value(Unit)

            // when
            runBlocking { viewModel.signInWithEmail(email, password) }

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
                signInWithEmailUseCase.addParams(any(), any()).invoke()
            } returns Either.Error(error)

            // when
            runBlocking { viewModel.signInWithEmail("dummy@mail.com", "qwerty") }

            // then
            verifySequence {
                authResultObserver.onChanged(EventState.Loading)
                authResultObserver.onChanged(EventState.Failure(error))
            }
        }
    }
}
