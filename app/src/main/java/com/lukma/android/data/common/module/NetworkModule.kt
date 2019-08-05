package com.lukma.android.data.common.module

import com.lukma.android.BuildConfig
import com.lukma.android.data.common.entity.RetrofitType
import com.lukma.android.data.common.interceptor.ApiInterceptor
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

    single(named(RetrofitType.DEFAULT.value)) {
        Retrofit.Builder().apply {
            addConverterFactory(JacksonConverterFactory.create())
        }
    }

    single(named(RetrofitType.BASIC_AUTH.value)) {
        get<Retrofit.Builder>(named(RetrofitType.DEFAULT.value)).apply {
            val okhttp = get<OkHttpClient.Builder>().apply {
                addInterceptor(ApiInterceptor(RetrofitType.BASIC_AUTH))
            }.build()
            client(okhttp)
        }
    }

    single(named(RetrofitType.TOKEN.value)) {
        get<Retrofit.Builder>(named(RetrofitType.DEFAULT.value)).apply {
            val okhhtp = get<OkHttpClient.Builder>().apply {
                addInterceptor(ApiInterceptor(RetrofitType.TOKEN))
            }.build()
            client(okhhtp)
        }
    }
}
