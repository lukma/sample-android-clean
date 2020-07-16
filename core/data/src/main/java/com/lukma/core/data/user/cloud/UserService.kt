package com.lukma.core.data.user.cloud

import com.lukma.core.data.common.BaseResponse
import com.lukma.core.data.user.cloud.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("v1/searchUser")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page_size") pageSize: Int = 20,
        @Query("offset") offset: Int = 0
    ): BaseResponse<List<UserResponse>>
}
