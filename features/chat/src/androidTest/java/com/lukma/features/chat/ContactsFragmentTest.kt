package com.lukma.features.chat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.lukma.core.domain.Either
import com.lukma.core.domain.user.User
import com.lukma.core.domain.user.usecase.SearchUsersUseCase
import com.lukma.core.test.android.RecyclerViewMatcher
import com.lukma.features.chat.contacts.ContactsFragment
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

class ContactsFragmentTest : KoinTest {
    private val searchUsersUseCase: SearchUsersUseCase = mockk()
    private val mockNavController: NavController = mockk(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { searchUsersUseCase }
            })
        }
    }

    @Test
    fun testLoadUsersSuccessfully() {
        // given
        coEvery {
            searchUsersUseCase.addParams(any(), any()).invoke()
        } returns Either.Value(usersData)

        // when
        launchScreen()
        Thread.sleep(1000)

        // then
        usersData.forEachIndexed { index, user ->
            onView(RecyclerViewMatcher.withId(R.id.contactRecyclerView).atPosition(index))
                .check(matches(hasDescendant(withText(user.displayName))))
        }
    }

    @Test
    fun testOpenExistingMessages() {
        // given
        coEvery {
            searchUsersUseCase.addParams(any(), any()).invoke()
        } returns Either.Value(usersData)

        // when
        launchScreen()
        Thread.sleep(1000)
        onView(RecyclerViewMatcher.withId(R.id.contactRecyclerView).atPosition(0)).perform(click())

        // then
        val direction = slot<NavDirections>()
        verify { mockNavController.navigate(capture(direction)) }
        assertTrue(direction.captured.actionId == com.lukma.android.R.id.action_to_chatMessagesFragment)
        assertTrue(direction.captured.arguments["to"] == usersData.first().email)
    }

    private fun launchScreen() {
        launchFragmentInContainer(themeResId = com.lukma.android.R.style.AppTheme) {
            ContactsFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }
    }

    companion object {
        private val usersData = listOf(
            User(
                email = "dummy@mail.com",
                displayName = "dummy",
                photoUrl = "https://someurl"
            )
        )
    }
}
