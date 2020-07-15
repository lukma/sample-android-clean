package com.lukma.core.domain.chat

import com.lukma.core.domain.Either
import com.lukma.core.domain.chat.usecase.GetChatMessagesUseCase
import com.lukma.core.domain.user.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class GetChatMessagesUseCaseTest {
    private val chatRepository: ChatRepository = mockk()
    private val useCase = GetChatMessagesUseCase(chatRepository)

    @Test
    fun `given success process when get chat messages then got data`() {
        // given
        val roomId = "someId"
        val messages = flowOf(
            listOf(
                ChatMessage(
                    content = "lorem ipsum",
                    createdBy = User(
                        email = "dummy@mail.com",
                        displayName = "dummy"
                    ),
                    createdAt = Date()
                )
            )
        )
        coEvery { chatRepository.getChatMessages(any()) } returns messages

        // when
        val result = runBlocking { useCase.addParams(roomId).invoke() }

        // then
        coVerify { chatRepository.getChatMessages(roomId) }
        val expected = Either.Value(messages)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when get chat messages then got error`() {
        // given
        val error = Exception("failed")
        coEvery { chatRepository.getChatMessages(any()) } throws error

        // when
        val result = runBlocking { useCase.addParams("1").invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
