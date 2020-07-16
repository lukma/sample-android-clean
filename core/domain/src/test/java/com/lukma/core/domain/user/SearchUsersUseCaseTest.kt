package com.lukma.core.domain.user

import com.lukma.core.domain.Either
import com.lukma.core.domain.ListConfig
import com.lukma.core.domain.user.usecase.SearchUsersUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SearchUsersUseCaseTest {
    private val userRepository: UserRepository = mockk()
    private val useCase = SearchUsersUseCase(userRepository)

    @Test
    fun `given success process when search users then got data`() {
        // given
        val query = "sample"
        val config = ListConfig()
        val users = listOf(
            User(
                email = "dummy@mail.com",
                displayName = "dummy"
            )
        )
        coEvery { userRepository.searchUsers(any(), any()) } returns users

        // when
        val result = runBlocking { useCase.addParams(query, config).invoke() }

        // then
        coVerify { userRepository.searchUsers(query, config) }
        val expected = Either.Value(users)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when search users then got error`() {
        // given
        val error = Exception("failed")
        coEvery { userRepository.searchUsers(any(), any()) } throws error

        // when
        val result = runBlocking { useCase.addParams("sample").invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
