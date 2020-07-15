package com.lukma.core.data

import com.lukma.core.data.user.cloud.UserService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val networkModule = module {
    factory {
        OkHttpClient.Builder()
            .build()
    }

    factory {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.ROOT_API_URL)
            .build()
    }

    single { get<Retrofit>().create<UserService>() }
}
