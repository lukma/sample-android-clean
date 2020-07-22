package com.lukma.android

import androidx.lifecycle.Observer
import com.lukma.android.main.MainViewModel
import com.lukma.core.domain.Either
import com.lukma.core.domain.account.Profile
import com.lukma.core.domain.account.usecase.GetProfileUseCase
import com.lukma.core.domain.auth.usecase.IsLoggedInUseCase
import com.lukma.core.test.TaskExecutorExtension
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
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
class MainViewModelTest : KoinTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private val isLoggedInUseCase: IsLoggedInUseCase = mockk()
    private val getProfileUseCase: GetProfileUseCase = mockk()

    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        stopKoin()
        startKoin {
            modules(module {
                single { isLoggedInUseCase }
                single { getProfileUseCase }
            })
        }
        viewModel = MainViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Nested
    inner class IsLogged {
        private val isLoggedObserver: Observer<Boolean> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.isLoggedIn.observeForever(isLoggedObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.isLoggedIn.removeObserver(isLoggedObserver)
        }

        @Test
        fun `given exist user when check is logged then notify observer with true`() {
            // given
            coEvery { isLoggedInUseCase.invoke() } returns Either.Value(true)

            // when
            runBlocking { viewModel.isLogged() }

            // then
            verify { isLoggedObserver.onChanged(true) }
        }

        @Test
        fun `given null user when check is logged then notify observer with false`() {
            // given
            val error = Exception("failed")
            coEvery { isLoggedInUseCase.invoke() } returns Either.Error(error)

            // when
            runBlocking { viewModel.isLogged() }

            // then
            verify { isLoggedObserver.onChanged(false) }
        }
    }

    @Nested
    inner class GetProfile {
        private val profileObserver: Observer<Profile> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.profile.observeForever(profileObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.profile.removeObserver(profileObserver)
        }

        @Test
        fun `given exist user when get profile then notify observer with data`() {
            // given
            val profile = Profile(
                email = "dummy@mail.com",
                displayName = "dummy",
                photoUrl = "https://someurl"
            )
            coEvery { getProfileUseCase.invoke() } returns Either.Value(profile)

            // when
            runBlocking { viewModel.getProfile() }

            // then
            verify { profileObserver.onChanged(profile) }
        }

        @Test
        fun `given null user when get profile then do nothing`() {
            // given
            val error = Exception("failed")
            coEvery { getProfileUseCase.invoke() } returns Either.Error(error)

            // when
            runBlocking { viewModel.getProfile() }

            // then
            verify(exactly = 0) { profileObserver.onChanged(any()) }
        }
    }
}
