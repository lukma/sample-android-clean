package com.lukma.core.domain.chat

import com.lukma.core.domain.Either
import com.lukma.core.domain.chat.usecase.SendChatMessageUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SendChatMessageUseCaseTest {
    private val chatRepository: ChatRepository = mockk()
    private val useCase = SendChatMessageUseCase(chatRepository)

    @Test
    fun `given success process when send chat message then got room id`() {
        // given
        val action = SendMessageAction.New(
            to = "dummy@mail.com",
            content = "lorem ipsum"
        )
        coJustRun { chatRepository.sendMessage(any()) }

        // when
        val result = runBlocking { useCase.addParams(action).invoke() }

        // then
        coVerify { chatRepository.sendMessage(action) }
        val expected = Either.Value(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when send chat message then got error`() {
        // given
        val error = Exception("failed")
        coEvery { chatRepository.sendMessage(any()) } throws error

        // when
        val result = runBlocking {
            useCase.addParams(SendMessageAction.New("dummy@mail.com", "lorem ipsum")).invoke()
        }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
