package com.lukma.clean.data.common.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lukma.clean.data.common.network.interceptor.ApiAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

enum class OKHttpType {
    OKHTTP_BASIC_AUTH,
    OKHTTP_BEARER
}

enum class RetrofitType {
    RETROFIT_DEFAULT,
    RETROFIT_BASIC_AUTH,
    RETROFIT_BEARER
}

val networkModule = module {
    factory {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
    }

    single(
        name = OKHttpType.OKHTTP_BASIC_AUTH.name,
        definition = {
            get<OkHttpClient.Builder>()
                .addInterceptor(ApiAuthInterceptor(ApiAuthInterceptor.Type.BASIC_AUTH))
                .build()
        }
    )

    single(
        name = OKHttpType.OKHTTP_BEARER.name,
        definition = {
            get<OkHttpClient.Builder>()
                .addInterceptor(ApiAuthInterceptor(ApiAuthInterceptor.Type.BEARER))
                .build()
        }
    )

    single(
        name = RetrofitType.RETROFIT_BASIC_AUTH.name,
        definition = {
            Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get(OKHttpType.OKHTTP_BASIC_AUTH.name))
        }
    )

    single(
        name = RetrofitType.RETROFIT_BEARER.name,
        definition = {
            Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get(OKHttpType.OKHTTP_BEARER.name))
        }
    )
}