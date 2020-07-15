package com.lukma.core.data.auth

import android.net.Uri
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AuthRepositoryTest {
    private val firebaseAuth: FirebaseAuth = mockk()
    private val repository = AuthRepositoryData(firebaseAuth)

    @BeforeEach
    fun setup() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")

        mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) } returns false

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockk()
    }

    @Nested
    inner class IsLoggedIn {

        @Test
        fun `given exist user when check is logged then got true`() {
            // given
            every { firebaseAuth.currentUser } returns mockk()

            // when
            val result = runBlocking { repository.isLoggedIn() }

            // then
            assertTrue(result)
        }

        @Test
        fun `given null user when check is logged then got false`() {
            // given
            every { firebaseAuth.currentUser } returns null

            // when
            val result = runBlocking { repository.isLoggedIn() }

            // then
            assertFalse(result)
        }
    }

    @Nested
    inner class SignInWithEmail {

        @Test
        fun `given success process when sign in with email then got nothing`() {
            // given
            val email = "dummy@mail.com"
            val password = "qwerty"
            coEvery {
                firebaseAuth.signInWithEmailAndPassword(any(), any()).await()
            } returns mockk()

            // when
            val result = runBlocking { repository.signInWithEmail(email, password) }

            // then
            verify { firebaseAuth.signInWithEmailAndPassword(email, password) }
            assertEquals(Unit, result)
        }

        @Test
        fun `given fail process when sign in with email then throw error`() {
            // given
            val error = Exception("failed")
            every { firebaseAuth.signInWithEmailAndPassword(any(), any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.signInWithEmail("dummy@mail.com", "qwerty") }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }

    @Nested
    inner class SignUpWithEmail {

        @Test
        fun `given success process when sign up with email then got nothing`() {
            // given
            val email = "dummy@mail.com"
            val password = "qwerty"
            val displayName = "dummy"
            coEvery {
                firebaseAuth.createUserWithEmailAndPassword(any(), any()).await()
            } returns mockk()
            val profileChangeRequest = slot<UserProfileChangeRequest>()
            coEvery {
                firebaseAuth.currentUser?.updateProfile(capture(profileChangeRequest))?.await()
            } returns mockk()

            // when
            val result = runBlocking { repository.signUpWithEmail(email, password, displayName) }

            // then
            verify { firebaseAuth.createUserWithEmailAndPassword(email, password) }
            assertEquals(displayName, profileChangeRequest.captured.displayName)
            assertEquals(Unit, result)
        }

        @Test
        fun `given fail process when sign up with email then throw error`() {
            // given
            val error = Exception("failed")
            every { firebaseAuth.createUserWithEmailAndPassword(any(), any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.signUpWithEmail("dummy@mail.com", "qwerty", "dummy") }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }
}
