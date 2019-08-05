package com.lukma.android.data.common.module

import android.content.Context
import com.lukma.android.BuildConfig
import com.lukma.android.data.auth.AuthDataRepository
import com.lukma.android.data.content.ContentDataRepository
import com.lukma.android.data.preference.PreferenceDataRepository
import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.content.ContentRepository
import com.lukma.android.domain.preference.PreferenceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy

val repositoryDataModule = module {
    single {
        androidContext().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    factoryBy<PreferenceRepository, PreferenceDataRepository>()

    factoryBy<AuthRepository, AuthDataRepository>()

    factoryBy<ContentRepository, ContentDataRepository>()
}
