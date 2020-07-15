package com.lukma.core.domain.auth

import com.lukma.core.domain.Either
import com.lukma.core.domain.auth.usecase.IsLoggedInUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IsLoggedInUseCaseTest {
    private val authRepository: AuthRepository = mockk()
    private val useCase = IsLoggedInUseCase(authRepository)

    @Test
    fun `given success process when check is logged then got value`() {
        // given
        val isLoggedIn = true
        coEvery { authRepository.isLoggedIn() } returns isLoggedIn

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        val expected = Either.Value(isLoggedIn)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when check is logged then got error`() {
        // given
        val error = Exception("failed")
        coEvery { authRepository.isLoggedIn() } throws error

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
