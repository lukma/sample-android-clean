package com.lukma.clean.data.common.module

import com.lukma.clean.BuildConfig
import com.lukma.clean.data.auth.AuthApi
import com.lukma.clean.data.content.ContentApi
import org.koin.dsl.module.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit.Builder>(RetrofitType.BASIC_AUTH.value)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(AuthApi::class.java)
    }

    single {
        get<Retrofit.Builder>(RetrofitType.BEARER.value)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(ContentApi::class.java)
    }
}
