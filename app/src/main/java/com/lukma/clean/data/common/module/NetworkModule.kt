package com.lukma.clean.data.common.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lukma.clean.BuildConfig
import com.lukma.clean.data.common.interceptor.ApiAuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

enum class RetrofitType(val value: String) {
    BASIC_AUTH("RETROFIT_BASIC_AUTH"),
    BEARER("RETROFIT_BEARER")
}

val networkModule = module {
    factory {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logInterceptor)
            }
        }
    }

    single(
        qualifier = named(RetrofitType.BASIC_AUTH.value),
        definition = {
            Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(
                    get<OkHttpClient.Builder>()
                        .addInterceptor(ApiAuthInterceptor(ApiAuthInterceptor.Type.BASIC_AUTH))
                        .build()
                )
        }
    )

    single(
        qualifier = named(RetrofitType.BEARER.value),
        definition = {
            Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(
                    get<OkHttpClient.Builder>()
                        .addInterceptor(ApiAuthInterceptor(ApiAuthInterceptor.Type.BEARER))
                        .build()
                )
        }
    )
}
