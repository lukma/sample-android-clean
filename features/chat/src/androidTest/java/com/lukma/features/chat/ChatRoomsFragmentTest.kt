package com.lukma.features.chat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.lukma.core.domain.Either
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.usecase.GetChatRoomsUseCase
import com.lukma.core.domain.user.User
import com.lukma.core.test.android.RecyclerViewMatcher
import com.lukma.features.chat.rooms.ChatRoomsFragment
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.*

class ChatRoomsFragmentTest : KoinTest {
    private val getChatRoomsUseCase: GetChatRoomsUseCase = mockk()
    private val mockNavController: NavController = mockk(relaxUnitFun = true)

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { getChatRoomsUseCase }
            })
        }
    }

    @Test
    fun testLoadRoomsSuccessfully() {
        // given
        coEvery { getChatRoomsUseCase.invoke() } returns Either.Value(flowOf(roomsData))

        // when
        launchScreen()

        // then
        roomsData.forEachIndexed { index, room ->
            onView(RecyclerViewMatcher.withId(R.id.roomRecyclerView).atPosition(index))
                .check(matches(hasDescendant(withText(room.lastMessage.content))))
        }
    }

    @Test
    fun testOpenNewMessages() {
        // given
        coEvery { getChatRoomsUseCase.invoke() } returns Either.Value(flowOf(roomsData))

        // when
        launchScreen()
        onView(withId(R.id.composeButton)).perform(click())

        // then
        val direction = slot<NavDirections>()
        verify { mockNavController.navigate(capture(direction)) }
        assertTrue(direction.captured.actionId == com.lukma.android.R.id.action_to_contactsFragment)
    }

    @Test
    fun testOpenExistingMessages() {
        // given
        coEvery { getChatRoomsUseCase.invoke() } returns Either.Value(flowOf(roomsData))

        // when
        launchScreen()
        onView(RecyclerViewMatcher.withId(R.id.roomRecyclerView).atPosition(0)).perform(click())

        // then
        val direction = slot<NavDirections>()
        verify { mockNavController.navigate(capture(direction)) }
        assertTrue(direction.captured.actionId == com.lukma.android.R.id.action_to_chatMessagesFragment)
        assertTrue(direction.captured.arguments["roomId"] == roomsData.first().id)
    }

    private fun launchScreen() {
        launchFragmentInContainer(themeResId = com.lukma.android.R.style.AppTheme) {
            ChatRoomsFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }
    }

    companion object {
        private val roomsData = listOf(
            ChatRoom(
                id = "someId",
                members = listOf("dummy@mail.com"),
                lastMessage = ChatMessage(
                    content = "lorem ipsum",
                    createdBy = User(
                        email = "dummy@mail.com",
                        displayName = "dummy"
                    ),
                    createdAt = Date()
                )
            )
        )
    }
}
