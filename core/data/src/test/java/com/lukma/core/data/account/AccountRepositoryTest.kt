package com.lukma.core.data.account

import com.google.firebase.auth.FirebaseAuth
import com.lukma.core.data.common.NotLoggedInException
import com.lukma.core.domain.account.Profile
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AccountRepositoryTest {
    private val firebaseAuth: FirebaseAuth = mockk()
    private val repository = AccountRepositoryData(firebaseAuth)

    @Nested
    inner class GetProfile {

        @Test
        fun `given exist user when get profile then got data`() {
            // given
            val email = "dummy@mail.com"
            val displayName = "dummy"
            val photoUrl = "https://someurl"
            every { firebaseAuth.currentUser } returns mockk {
                every { this@mockk.email } returns email
                every { this@mockk.displayName } returns displayName
                every { this@mockk.photoUrl?.path } returns photoUrl
            }

            // when
            val result = runBlocking { repository.getProfile() }

            // then
            val expected = Profile(email, displayName, photoUrl)
            assertEquals(expected, result)
        }

        @Test
        fun `given null user when get profile then throw error`() {
            // given
            every { firebaseAuth.currentUser } returns null

            // when
            val result = runCatching {
                runBlocking { repository.getProfile() }
            }.exceptionOrNull()

            // then
            assertTrue(result is NotLoggedInException)
        }

        @Test
        fun `given null email when get profile then throw error`() {
            // given
            every { firebaseAuth.currentUser?.email } returns null

            // when
            val result = runCatching {
                runBlocking { repository.getProfile() }
            }.exceptionOrNull()

            // then
            assertTrue(result is NotLoggedInException)
        }
    }
}
