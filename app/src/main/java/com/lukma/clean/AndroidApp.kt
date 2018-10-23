package com.lukma.clean

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.lukma.clean.di.*
import org.koin.android.ext.android.startKoin

class AndroidApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        startKoin(this, listOf(
                appModule,
                roomModule,
                networkModule,
                apiModule,
                dataModule,
                useCaseModule,
                viewModelModule
        ))
    }
}