package com.lukma.core.domain.chat

import com.lukma.core.domain.Either
import com.lukma.core.domain.chat.usecase.GetChatRoomByMembersUseCase
import com.lukma.core.domain.user.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class GetChatRoomByMembersUseCaseTest {
    private val chatRepository: ChatRepository = mockk()
    private val useCase = GetChatRoomByMembersUseCase(chatRepository)

    @Test
    fun `given success process when get chat room by receiver then got data`() {
        // given
        val members = listOf("dummy@mail.com")
        val room = ChatRoom(
            id = "someId",
            members = members,
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
        coEvery { chatRepository.getChatRoomByMembers(any()) } returns room

        // when
        val result = runBlocking { useCase.addParams(members).invoke() }

        // then
        coVerify { chatRepository.getChatRoomByMembers(members) }
        val expected = Either.Value(room)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when get chat room by receiver then got error`() {
        // given
        val error = Exception("failed")
        coEvery { chatRepository.getChatRoomByMembers(any()) } throws error

        // when
        val result = runBlocking { useCase.addParams(listOf("dummy@mail.com")).invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
