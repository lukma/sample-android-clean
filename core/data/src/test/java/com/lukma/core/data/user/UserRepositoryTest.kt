package com.lukma.core.data.user

import com.lukma.core.data.common.BaseResponse
import com.lukma.core.data.user.cloud.UserService
import com.lukma.core.data.user.cloud.response.UserResponse
import com.lukma.core.data.user.local.UserDao
import com.lukma.core.data.user.local.UserTable
import com.lukma.core.domain.ListConfig
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class UserRepositoryTest {
    private val userDao: UserDao = mockk()
    private val userService: UserService = mockk()
    private val repository = UserRepositoryData(userDao, userService)

    @Nested
    inner class SearchUsers {

        @Test
        fun `given success process when search users then got data`() {
            // given
            val query = "sample"
            val config = ListConfig()
            val usersResponse = BaseResponse(
                listOf(
                    UserResponse(
                        email = "dummy@mail.com",
                        displayName = "dummy",
                        photoUrl = "https://someurl"
                    )
                )
            )
            val userTables = listOf(
                UserTable(
                    email = "dummy@mail.com",
                    displayName = "dummy",
                    photoUrl = "https://someurl"
                )
            )
            coEvery { userService.searchUsers(any(), any(), any()) } returns usersResponse
            coEvery { userDao.finds(any(), any(), any()) } returns userTables

            // when
            val result = runBlocking { repository.searchUsers(query, config) }

            // then
            coEvery { userService.searchUsers(query, config.limit, config.offset) }
            val expected = userTables.map(::transform)
            assertEquals(expected, result)
        }

        @Test
        fun `given fail process when search users then throw error`() {
            // given
            val error = Exception("failed")
            coEvery { userService.searchUsers(any(), any(), any()) } throws error
            coEvery { userDao.finds(any(), any(), any()) } returns listOf()

            // when
            val result = runCatching {
                runBlocking { repository.searchUsers("sample", ListConfig()) }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }
}
