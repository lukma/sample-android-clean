package com.lukma.core.data

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, BuildConfig.LIBRARY_PACKAGE_NAME
        ).build()
    }

    single { get<AppDatabase>().userDao() }
}
