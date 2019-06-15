package com.lukma.clean.data.common.module

import com.lukma.clean.BuildConfig
import com.lukma.clean.data.auth.cloud.AuthApi
import com.lukma.clean.data.common.entity.RetrofitType
import com.lukma.clean.data.content.cloud.ContentApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit.Builder>(named(RetrofitType.BASIC_AUTH.value))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(AuthApi::class.java)
    }

    single {
        get<Retrofit.Builder>(named(RetrofitType.TOKEN.value))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(ContentApi::class.java)
    }
}
