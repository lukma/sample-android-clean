package com.lukma.core.domain.auth

import com.lukma.core.domain.Either
import com.lukma.core.domain.auth.usecase.SignUpWithEmailUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignUpWithEmailUseCaseTest {
    private val authRepository: AuthRepository = mockk()
    private val useCase = SignUpWithEmailUseCase(authRepository)

    @Test
    fun `given success process when sign up with email then got nothing`() {
        // given
        val email = "dummy@mail.com"
        val password = "qwerty"
        val displayName = "dummy"
        coJustRun { authRepository.signUpWithEmail(any(), any(), any()) }

        // when
        val result = runBlocking { useCase.addParams(email, password, displayName).invoke() }

        // then
        coVerify { authRepository.signUpWithEmail(email, password, displayName) }
        val expected = Either.Value(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when sign up with email then got error`() {
        // given
        val error = Exception("failed")
        coEvery { authRepository.signUpWithEmail(any(), any(), any()) } throws error

        // when
        val result = runBlocking { useCase.addParams("dummy@mail.com", "qwerty", "dummy").invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
