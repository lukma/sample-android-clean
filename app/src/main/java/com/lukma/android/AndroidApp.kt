package com.lukma.android

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.lukma.core.data.databaseModule
import com.lukma.core.data.networkModule
import com.lukma.core.data.repositoryModule
import com.lukma.core.domain.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class AndroidApp : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }
    }
}
