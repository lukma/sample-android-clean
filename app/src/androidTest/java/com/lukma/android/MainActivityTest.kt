package com.lukma.android

import com.lukma.android.main.MainActivity
import com.lukma.core.domain.Either
import com.lukma.core.domain.account.Profile
import com.lukma.core.domain.account.usecase.GetProfileUseCase
import com.lukma.core.domain.auth.usecase.IsLoggedInUseCase
import com.lukma.core.domain.chat.usecase.GetChatRoomsUseCase
import de.mannodermaus.junit5.ActivityScenarioExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class MainActivityTest : KoinTest {
    private val isLoggedInUseCase: IsLoggedInUseCase = mockk()
    private val getProfileUseCase: GetProfileUseCase = mockk()
    private val getChatRoomsUseCase: GetChatRoomsUseCase = mockk()

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { isLoggedInUseCase }
                single { getProfileUseCase }
                single { getChatRoomsUseCase }
            })
        }
        ActivityScenarioExtension.launch<MainActivity>()
    }

    @Test
    fun givenLaunchedWhenIsLoggedInThenShowHome() {
        // given
        val isLoggedIn = true
        val profile = Profile(
            email = "dummy@mail.com",
            displayName = "dummy"
        )

        // when
        coEvery { isLoggedInUseCase.invoke() } returns Either.Value(isLoggedIn)
        coEvery { getProfileUseCase.invoke() } returns Either.Value(profile)
        coEvery { getChatRoomsUseCase.invoke() } returns Either.Value(flowOf(listOf()))

        // then
//        verify(exactly = 0) { mockNavController.navigate(any<NavDirections>()) }
    }

    @Test
    fun givenLaunchedWhenIsNotLoggedInThenGoToLogin() {
        // given
        val isLoggedIn = false

        // when
        coEvery { isLoggedInUseCase.invoke() } returns Either.Value(isLoggedIn)

        // then
//        val direction = slot<NavDirections>()
//        verify { mockNavController.navigate(capture(direction)) }
//        assertTrue(direction.captured.actionId == R.id.action_to_loginFragment)
    }
}
