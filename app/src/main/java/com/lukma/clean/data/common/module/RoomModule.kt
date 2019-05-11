package com.lukma.clean.data.common.module

import androidx.room.Room
import com.lukma.clean.BuildConfig
import com.lukma.clean.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, BuildConfig.APPLICATION_ID)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().authDao() }

    single { get<AppDatabase>().contentDao() }
}
