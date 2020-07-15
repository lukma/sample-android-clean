package com.lukma.core.data.user.cloud

import com.lukma.core.data.common.BaseResponse
import com.lukma.core.data.user.cloud.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("/api/searchUser")
    suspend fun searchUsers(@Query("query") query: String): BaseResponse<List<UserResponse>>
}
