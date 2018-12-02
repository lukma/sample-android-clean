package com.lukma.clean.data.auth.store.cloud

import com.lukma.clean.data.auth.store.cloud.response.AuthResponse
import com.lukma.clean.data.auth.store.cloud.response.RegisterResponse
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("account/auth/login")
    fun authorize(
        @Field("username") usernameOrEmail: String,
        @Field("password") password: String
    ): Flowable<AuthResponse>

    @FormUrlEncoded
    @POST("account/auth/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullname") fullName: String,
        @Field("email") email: String
    ): Flowable<RegisterResponse>

    @FormUrlEncoded
    @POST("account/auth/refreshToken")
    fun refreshToken(
        @Field("refresh_token") token: String
    ): Flowable<AuthResponse>
}