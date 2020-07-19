package com.lukma.features.chat

import androidx.lifecycle.Observer
import com.lukma.core.domain.Either
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.usecase.GetChatRoomsUseCase
import com.lukma.core.domain.user.User
import com.lukma.core.test.TaskExecutorExtension
import com.lukma.features.chat.rooms.ChatRoomsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
import java.util.*

@ExperimentalCoroutinesApi
@ExtendWith(TaskExecutorExtension::class)
class ChatRoomsViewModelTest : KoinTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private val getChatRoomsUseCase: GetChatRoomsUseCase = mockk()
    private lateinit var viewModel: ChatRoomsViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        stopKoin()
        startKoin {
            modules(module {
                single { getChatRoomsUseCase }
            })
        }
        viewModel = ChatRoomsViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Nested
    inner class GetRooms {
        private val roomsObserver: Observer<List<ChatRoom>> = mockk(relaxUnitFun = true)

        @BeforeEach
        fun start() {
            viewModel.rooms.observeForever(roomsObserver)
        }

        @AfterEach
        fun stop() {
            viewModel.rooms.removeObserver(roomsObserver)
        }

        @Test
        fun `given success process when get chat rooms then notify observer with data`() {
            // given
            val rooms = listOf(
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
            coEvery { getChatRoomsUseCase.invoke() } returns Either.Value(flowOf(rooms))

            // when
            runBlocking { viewModel.getRooms() }

            // then
            verify { roomsObserver.onChanged(rooms) }
        }

        @Test
        fun `given fail process when get chat rooms then do nothing`() {
            // given
            val error = Exception("failed")
            coEvery { getChatRoomsUseCase.invoke() } returns Either.Error(error)

            // when
            runBlocking { viewModel.getRooms() }

            // then
            verify(exactly = 0) { roomsObserver.onChanged(any()) }
        }
    }
}
