package com.lukma.android.data.common.module

import androidx.room.Room
import com.lukma.android.BuildConfig
import com.lukma.android.data.AppDatabase
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
