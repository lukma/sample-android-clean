package com.lukma.core.data.user

import com.lukma.core.data.common.BaseResponse
import com.lukma.core.data.common.ErrorResponse
import com.lukma.core.data.user.cloud.UserService
import com.lukma.core.data.user.cloud.response.UserResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class UserServiceTest {
    private val mockWebServer = MockWebServer()
    private val userService: UserService = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(mockWebServer.url("/"))
        .build()
        .create()

    @Nested
    inner class SearchUsers {

        @Test
        fun `given success process when search users then got data`() {
            // given
            val response = """
            {
                    "data": [
                        {
                            "email": "dummy@mail.com",
                            "displayName": "dummy",
                            "photoUrl": "https://someurl"
                        }
                    ]
                }
            """
            mockWebServer.enqueue(MockResponse().setBody(response))

            // when
            val result = runBlocking { userService.searchUsers("sample") }

            // then
            val expected = BaseResponse(
                data = listOf(
                    UserResponse(
                        email = "dummy@mail.com",
                        displayName = "dummy",
                        photoUrl = "https://someurl"
                    )
                )
            )
            assertEquals(expected, result)
        }

        @Test
        fun `given fail process when search users then throw error`() {
            // given
            val response = """
                {
                    "error": {
                        "code": "ERR-XXX",
                        "description": "Failed"
                    }
                }
            """
            mockWebServer.enqueue(MockResponse().setBody(response))

            // when
            val result = runBlocking { userService.searchUsers("sample") }

            // then
            val expected = BaseResponse<List<UserResponse>>(
                error = ErrorResponse(
                    code = "ERR-XXX",
                    description = "Failed"
                )
            )
            assertEquals(expected, result)
        }
    }
}
