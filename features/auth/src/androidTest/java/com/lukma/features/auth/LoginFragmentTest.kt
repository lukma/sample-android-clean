package com.lukma.features.auth

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.lukma.core.domain.Either
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import com.lukma.features.auth.login.LoginFragment
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class LoginFragmentTest : KoinTest {
    private val signInWithEmailUseCase: SignInWithEmailUseCase = mockk()
    private val mockNavController: NavController = mockk(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { signInWithEmailUseCase }
            })
        }

        launchFragmentInContainer(themeResId = com.lukma.android.R.style.AppTheme) {
            LoginFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }
    }

    @Test
    fun testLoginSuccessfully() {
        // given
        val email = "dummy@mail.com"
        val password = "qwerty"
        coEvery {
            signInWithEmailUseCase.addParams(email, password).invoke()
        } returns Either.Value(Unit)

        // when
        onView(withId(R.id.emailInput)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.passwordInput)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        // then
        val direction = slot<NavDirections>()
        verify { mockNavController.navigate(capture(direction)) }
        assertTrue(direction.captured.actionId == com.lukma.android.R.id.action_to_homeFragment)
    }

    @Test
    fun testLoginFailed() {
        // given
        val error = Exception("failed")
        coEvery {
            signInWithEmailUseCase.addParams(any(), any()).invoke()
        } returns Either.Error(error)

        // when
        onView(withId(R.id.emailInput)).perform(typeText("dummy@mail.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordInput)).perform(typeText("qwerty"), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        // then
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(error.message)))
    }
}
