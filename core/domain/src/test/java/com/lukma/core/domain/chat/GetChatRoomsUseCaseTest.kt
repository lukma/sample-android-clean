package com.lukma.core.domain.chat

import com.lukma.core.domain.Either
import com.lukma.core.domain.chat.usecase.GetChatRoomsUseCase
import com.lukma.core.domain.user.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class GetChatRoomsUseCaseTest {
    private val chatRepository: ChatRepository = mockk()
    private val useCase = GetChatRoomsUseCase(chatRepository)

    @Test
    fun `given success process when get chat rooms then got data`() {
        // given
        val rooms = flowOf(
            listOf(
                ChatRoom(
                    id = "someId",
                    members = listOf("dummy@mail.com"),
                    lastMessage = ChatMessage(
                        content = "lorem ipsum",
                        createdBy = User(
                            email = "dummy@mail.com",
                            displayName = "dummy",
                            photoUrl = "https://someurl"
                        ),
                        createdAt = Date()
                    )
                )
            )
        )
        coEvery { chatRepository.getChatRooms() } returns rooms

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        val expected = Either.Value(rooms)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when get chat rooms then got error`() {
        // given
        val error = Exception("failed")
        coEvery { chatRepository.getChatRooms() } throws error

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
