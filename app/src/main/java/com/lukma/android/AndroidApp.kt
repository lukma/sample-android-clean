package com.lukma.android

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.lukma.android.data.common.module.apiModule
import com.lukma.android.data.common.module.networkModule
import com.lukma.android.data.common.module.repositoryDataModule
import com.lukma.android.data.common.module.roomModule
import com.lukma.android.domain.common.module.useCaseModule
import com.lukma.android.presentation.common.module.thirdPartyModule
import com.lukma.android.presentation.common.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        startKoin {
            androidLogger()
            androidContext(this@AndroidApp)
            modules(
                listOf(
                    thirdPartyModule,
                    roomModule,
                    networkModule,
                    apiModule,
                    repositoryDataModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}