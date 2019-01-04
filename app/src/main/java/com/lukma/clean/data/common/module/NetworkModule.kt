package com.lukma.clean.data.common.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lukma.clean.data.common.interceptor.ApiAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

enum class RetrofitType(val value: String) {
    BASIC_AUTH("RETROFIT_BASIC_AUTH"),
    BEARER("RETROFIT_BEARER")
}

val networkModule = module {
    factory {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
    }

    single(
        name = RetrofitType.BASIC_AUTH.value,
        definition = {
            Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get<OkHttpClient.Builder>()
                    .addInterceptor(ApiAuthInterceptor(ApiAuthInterceptor.Type.BASIC_AUTH))
                    .build())
        }
    )

    single(
        name = RetrofitType.BEARER.value,
        definition = {
            Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get<OkHttpClient.Builder>()
                    .addInterceptor(ApiAuthInterceptor(ApiAuthInterceptor.Type.BEARER))
                    .build())
        }
    )
}
