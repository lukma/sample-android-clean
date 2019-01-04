package com.lukma.clean.data.content

import com.lukma.clean.data.content.entity.response.GetContentsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentApi {
    @GET("post/content")
    fun gets(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<GetContentsResponse>
}
