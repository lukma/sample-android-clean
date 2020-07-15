package com.lukma.core.domain.auth

import com.lukma.core.domain.Either
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SignInWithEmailUseCaseTest {
    private val authRepository: AuthRepository = mockk()
    private val useCase = SignInWithEmailUseCase(authRepository)

    @Test
    fun `given success process when sign in with email then got nothing`() {
        // given
        val email = "dummy@mail.com"
        val password = "qwerty"
        coJustRun { authRepository.signInWithEmail(any(), any()) }

        // when
        val result = runBlocking { useCase.addParams(email, password).invoke() }

        // then
        coVerify { authRepository.signInWithEmail(email, password) }
        val expected = Either.Value(Unit)
        assertEquals(expected, result)
    }

    @Test
    fun `given fail process when sign in with email then got error`() {
        // given
        val error = Exception("failed")
        coEvery { authRepository.signInWithEmail(any(), any()) } throws error

        // when
        val result = runBlocking { useCase.addParams("dummy@mail.com", "qwerty").invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
