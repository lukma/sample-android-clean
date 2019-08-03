package com.lukma.clean.data.auth.cloud

import com.lukma.clean.data.auth.cloud.response.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("account/auth/login")
    suspend fun authorize(
        @Field("username") usernameOrEmail: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("account/auth/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullName: String,
        @Field("email") email: String
    )

    @FormUrlEncoded
    @POST("account/auth/refreshToken")
    suspend fun refreshToken(
        @Field("refresh_token") token: String
    ): AuthResponse
}
