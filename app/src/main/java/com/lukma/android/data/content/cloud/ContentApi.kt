package com.lukma.android.data.content.cloud

import com.lukma.android.data.common.entity.ListDataResponse
import com.lukma.android.data.content.cloud.response.ContentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentApi {
    @GET("post/content")
    suspend fun gets(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ListDataResponse<ContentResponse>
}
