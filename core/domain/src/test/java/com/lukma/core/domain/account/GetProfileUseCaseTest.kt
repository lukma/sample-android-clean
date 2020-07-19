package com.lukma.core.domain.account

import com.lukma.core.domain.Either
import com.lukma.core.domain.account.usecase.GetProfileUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetProfileUseCaseTest {
    private val accountRepository: AccountRepository = mockk()
    private val useCase = GetProfileUseCase(accountRepository)

    @Test
    fun `given exist user when get profile then got data`() {
        // given
        val profile = Profile(
            email = "dummy@mail.com",
            displayName = "dummy",
            photoUrl = "https://someurl"
        )
        coEvery { accountRepository.getProfile() } returns profile

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        val expected = Either.Value(profile)
        assertEquals(expected, result)
    }

    @Test
    fun `given null user when get profile then got error`() {
        // given
        val error = Exception("failed")
        coEvery { accountRepository.getProfile() } throws error

        // when
        val result = runBlocking { useCase.invoke() }

        // then
        val expected = Either.Error(error)
        assertEquals(expected, result)
    }
}
