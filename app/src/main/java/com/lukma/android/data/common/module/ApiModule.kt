package com.lukma.android.data.common.module

import com.lukma.android.BuildConfig
import com.lukma.android.data.auth.cloud.AuthApi
import com.lukma.android.data.common.entity.RetrofitType
import com.lukma.android.data.content.cloud.ContentApi
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
