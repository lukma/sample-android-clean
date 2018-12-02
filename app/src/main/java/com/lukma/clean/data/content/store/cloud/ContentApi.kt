package com.lukma.clean.data.content.store.cloud

import com.lukma.clean.data.content.store.cloud.response.GetContentsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ContentApi {
    @GET("post/content")
    fun gets(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Flowable<GetContentsResponse>
}