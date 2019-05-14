package com.lukma.clean.data.common.module

import android.content.Context
import com.lukma.clean.BuildConfig
import com.lukma.clean.data.auth.AuthDataRepository
import com.lukma.clean.data.content.ContentDataRepository
import com.lukma.clean.data.preference.PreferenceDataRepository
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.content.ContentRepository
import com.lukma.clean.domain.preference.PreferenceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryDataModule = module {
    single {
        androidContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    single<PreferenceRepository> { PreferenceDataRepository(get()) }

    single<AuthRepository> { AuthDataRepository(get(), get()) }

    single<ContentRepository> { ContentDataRepository(get(), get()) }
}
