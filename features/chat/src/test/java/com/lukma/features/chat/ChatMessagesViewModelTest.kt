package com.lukma.features.chat

import androidx.lifecycle.Observer
import com.lukma.core.domain.Either
import com.lukma.core.domain.EventState
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.SendMessageAction
import com.lukma.core.domain.chat.usecase.GetChatMessagesUseCase
import com.lukma.core.domain.chat.usecase.GetChatRoomByMembersUseCase
import com.lukma.core.domain.chat.usecase.SendChatMessageUseCase
import com.lukma.core.domain.user.User
import com.lukma.features.chat.messages.ChatMessagesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flowOf
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
import java.util.*

@ExtendWith(TaskExecutorExtension::class)
class ChatMessagesViewModelTest : KoinTest {
    private val getChatRoomByMembersUseCase: GetChatRoomByMembersUseCase = mockk()
    private val getChatMessagesUseCase: GetChatMessagesUseCase = mockk()
    private val sendChatMessageUseCase: SendChatMessageUseCase = mockk()

    private lateinit var viewModel: ChatMessagesViewModel

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { getChatRoomByMembersUseCase }
                single { getChatMessagesUseCase }
                single { sendChatMessageUseCase }
            })
        }
        viewModel = ChatMessagesViewModel()
    }

    @Nested
    inner class GetRoom {
        private val roomObserver: Observer<ChatRoom> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.room.observeForever(roomObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.room.removeObserver(roomObserver)
        }

        @Test
        fun `given success process when get chat room by members then notify observer with data`() {
            // given
            val members = arrayListOf("other@mail.com")
            coEvery {
                getChatRoomByMembersUseCase.addParams(members).invoke()
            } returns Either.Value(roomData)

            // when
            runBlocking { viewModel.getRoom(members) }

            // then
            verify { roomObserver.onChanged(roomData) }
        }

        @Test
        fun `given fail process when get chat room by members then do nothing`() {
            // given
            val error = Exception("failed")
            coEvery {
                getChatRoomByMembersUseCase.addParams(any()).invoke()
            } returns Either.Error(error)

            // when
            runBlocking { viewModel.getRoom(listOf("other@mail.com")) }

            // then
            verify(exactly = 0) { roomObserver.onChanged(any()) }
        }
    }

    @Nested
    inner class GetMessages {
        private val messagesObserver: Observer<List<ChatMessage>> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.messages.observeForever(messagesObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.messages.removeObserver(messagesObserver)
        }

        @Test
        fun `given success process when get chat messages then notify observer with data`() {
            // given
            val roomId = "someId"
            coEvery {
                getChatMessagesUseCase.addParams(roomId).invoke()
            } returns Either.Value(flowOf(messagesData))

            // when
            runBlocking { viewModel.getMessages(roomId) }

            // then
            verify { messagesObserver.onChanged(messagesData) }
        }

        @Test
        fun `given fail process when get chat messages then do nothing`() {
            // given
            val error = Exception("failed")
            coEvery { getChatMessagesUseCase.addParams(any()).invoke() } returns Either.Error(error)

            // when
            runBlocking { viewModel.getMessages("someId") }

            // then
            verify(exactly = 0) { messagesObserver.onChanged(any()) }
        }
    }

    @Nested
    inner class SendMessage {
        private val sendMessageResultObserver: Observer<EventState<Unit>> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.sendMessageResult.observeForever(sendMessageResultObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.sendMessageResult.removeObserver(sendMessageResultObserver)
        }

        @Test
        fun `given success process when send new chat message then notify observer with room id`() {
            // given
            val action = SendMessageAction.New(
                to = "dummy@mail.com",
                content = "lorem ipsum"
            )
            val roomId = "someId"
            coEvery {
                sendChatMessageUseCase.addParams(action).invoke()
            } returns Either.Value(Unit)
            coEvery {
                getChatMessagesUseCase.addParams(roomId).invoke()
            } returns Either.Value(flowOf(listOf()))

            // when
            runBlocking { viewModel.sendMessage(action) }

            // then
            verifySequence {
                sendMessageResultObserver.onChanged(EventState.Loading)
                sendMessageResultObserver.onChanged(EventState.Success(Unit))
            }
        }

        @Test
        fun `given success process when send reply chat message then notify observer with room id`() {
            // given
            val action = SendMessageAction.Reply(
                roomId = "someId",
                content = "lorem ipsum"
            )
            coEvery {
                sendChatMessageUseCase.addParams(action).invoke()
            } returns Either.Value(Unit)

            // when
            runBlocking { viewModel.sendMessage(action) }

            // then
            verifySequence {
                sendMessageResultObserver.onChanged(EventState.Loading)
                sendMessageResultObserver.onChanged(EventState.Success(Unit))
            }
        }

        @Test
        fun `given fail process when send chat message then notify observer with error`() {
            // given
            val error = Exception("failed")
            coEvery { sendChatMessageUseCase.addParams(any()).invoke() } returns Either.Error(error)

            // when
            runBlocking { viewModel.sendMessage(mockk()) }

            // then
            verifySequence {
                sendMessageResultObserver.onChanged(EventState.Loading)
                sendMessageResultObserver.onChanged(EventState.Failure(error))
            }
        }
    }

    companion object {
        private val messageData = ChatMessage(
            content = "lorem ipsum",
            createdBy = User(
                email = "dummy@mail.com",
                displayName = "dummy"
            ),
            createdAt = Date()
        )
        private val messagesData = listOf(messageData)
        private val roomData = ChatRoom(
            id = "someId",
            members = listOf("dummy@mail.com"),
            lastMessage = messageData
        )
    }
}
