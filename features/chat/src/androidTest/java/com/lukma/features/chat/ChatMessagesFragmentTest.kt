package com.lukma.features.chat

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.lukma.core.domain.Either
import com.lukma.core.domain.account.Profile
import com.lukma.core.domain.account.usecase.GetProfileUseCase
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.usecase.GetChatMessagesUseCase
import com.lukma.core.domain.chat.usecase.GetChatRoomByMembersUseCase
import com.lukma.core.domain.chat.usecase.SendChatMessageUseCase
import com.lukma.core.domain.user.User
import com.lukma.core.test.android.RecyclerViewMatcher
import com.lukma.features.chat.messages.ChatMessagesFragment
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.*

class ChatMessagesFragmentTest : KoinTest {
    private val getProfileUseCase: GetProfileUseCase = mockk()
    private val getChatRoomByMembersUseCase: GetChatRoomByMembersUseCase = mockk()
    private val getChatMessagesUseCase: GetChatMessagesUseCase = mockk()
    private val sendChatMessageUseCase: SendChatMessageUseCase = mockk()

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { getProfileUseCase }
                single { getChatRoomByMembersUseCase }
                single { getChatMessagesUseCase }
                single { sendChatMessageUseCase }
            })
        }

        coEvery {
            getProfileUseCase.invoke()
        } returns Either.Value(
            Profile(
                email = "dummy@mail.com",
                displayName = "dummy",
                photoUrl = "https://someurl"
            )
        )
    }

    @DisplayName("given messages screen when load data success then show list of data")
    @Test
    fun testLoadMessagesSuccessfully() {
        // given
        val messagesData = listOf(
            ChatMessage(
                content = "lorem ipsum",
                createdBy = User(
                    email = "dummy@mail.com",
                    displayName = "dummy",
                    photoUrl = "https://someurl"
                ),
                createdAt = Date()
            )
        )
        coEvery {
            getChatMessagesUseCase.addParams(any()).invoke()
        } returns Either.Value(flowOf(messagesData))

        // when
        launchScreen()

        // then
        messagesData.forEachIndexed { index, message ->
            onView(RecyclerViewMatcher.withId(R.id.messageRecyclerView).atPosition(index))
                .check(matches(hasDescendant(withText(message.content))))
        }
    }

    private fun launchScreen() {
        val args = Bundle().apply {
            putString("roomId", "someId")
        }
        launchFragmentInContainer<ChatMessagesFragment>(
            fragmentArgs = args,
            themeResId = com.lukma.android.R.style.AppTheme
        )
    }
}
