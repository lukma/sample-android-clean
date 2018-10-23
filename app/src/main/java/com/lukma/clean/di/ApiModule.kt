package com.lukma.clean.di

import com.lukma.clean.BuildConfig
import com.lukma.clean.data.auth.store.cloud.AuthApi
import com.lukma.clean.data.content.store.cloud.ContentApi
import org.koin.dsl.module.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit.Builder>(RetrofitType.RETROFIT_BASIC_AUTH.name)
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(AuthApi::class.java)
    }

    single {
        get<Retrofit.Builder>(RetrofitType.RETROFIT_BEARER.name)
                .baseUrl(BuildConfig.BASE_URL)
                .build()
                .create(ContentApi::class.java)
    }
}