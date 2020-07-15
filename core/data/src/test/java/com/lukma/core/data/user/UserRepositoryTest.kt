package com.lukma.core.data.user

import com.lukma.core.data.common.BaseResponse
import com.lukma.core.data.user.cloud.UserService
import com.lukma.core.data.user.cloud.response.UserResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserRepositoryTest {
    private val userService: UserService = mockk()
    private val repository = UserRepositoryData(userService)

    @Nested
    inner class SearchUsers {

        @Test
        fun `given success process when search users then got data`() {
            // given
            val query = "sample"
            val users = listOf(
                UserResponse(
                    email = "dummy@mail.com",
                    displayName = "dummy"
                )
            )
            coEvery { userService.searchUsers(any()) } returns BaseResponse(users)

            // when
            val result = runBlocking { repository.searchUsers(query) }

            // then
            coEvery { userService.searchUsers(query) }
            val expected = users.map(::transform)
            assertEquals(expected, result)
        }

        @Test
        fun `given fail process when search users then throw error`() {
            // given
            val error = Exception("failed")
            coEvery { userService.searchUsers(any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.searchUsers("sample") }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }
}
