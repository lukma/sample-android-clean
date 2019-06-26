package com.lukma.clean.data.content.cloud

import com.lukma.clean.data.common.entity.ListDataResponse
import com.lukma.clean.data.content.cloud.response.ContentResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentApi {
    @GET("post/content")
    fun gets(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<ListDataResponse<ContentResponse>>
}
