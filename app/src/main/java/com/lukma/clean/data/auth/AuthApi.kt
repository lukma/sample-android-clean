package com.lukma.clean.data.auth

import com.lukma.clean.data.auth.entity.response.AuthResponse
import com.lukma.clean.data.auth.entity.response.RegisterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("account/auth/login")
    fun authorize(
        @Field("username") usernameOrEmail: String,
        @Field("password") password: String
    ): Deferred<AuthResponse>

    @FormUrlEncoded
    @POST("account/auth/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullName: String,
        @Field("email") email: String
    ): Deferred<RegisterResponse>

    @FormUrlEncoded
    @POST("account/auth/refreshToken")
    fun refreshToken(
        @Field("refresh_token") token: String
    ): Deferred<AuthResponse>
}
