package com.lukma.clean.data.common.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lukma.clean.BuildConfig
import com.lukma.clean.data.common.entity.RetrofitType
import com.lukma.clean.data.common.interceptor.ApiInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

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
        qualifier = named(RetrofitType.DEFAULT.value),
        definition = {
            Retrofit.Builder().apply {
                addConverterFactory(JacksonConverterFactory.create())
                addCallAdapterFactory(CoroutineCallAdapterFactory())
            }
        }
    )

    single(
        qualifier = named(RetrofitType.BASIC_AUTH.value),
        definition = {
            get<Retrofit.Builder>(named(RetrofitType.DEFAULT.value)).apply {
                val okhttp = get<OkHttpClient.Builder>().apply {
                    addInterceptor(ApiInterceptor(RetrofitType.BASIC_AUTH))
                }.build()
                client(okhttp)
            }
        }
    )

    single(
        qualifier = named(RetrofitType.TOKEN.value),
        definition = {
            get<Retrofit.Builder>(named(RetrofitType.DEFAULT.value)).apply {
                val okhhtp = get<OkHttpClient.Builder>().apply {
                    addInterceptor(ApiInterceptor(RetrofitType.TOKEN))
                }.build()
                client(okhhtp)
            }
        }
    )
}
