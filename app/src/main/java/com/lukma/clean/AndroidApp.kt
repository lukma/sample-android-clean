package com.lukma.clean

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.lukma.clean.data.common.module.apiModule
import com.lukma.clean.data.common.module.dataModule
import com.lukma.clean.data.common.module.networkModule
import com.lukma.clean.data.common.module.roomModule
import com.lukma.clean.domain.common.module.useCaseModule
import com.lukma.clean.ui.common.module.appModule
import com.lukma.clean.ui.common.module.viewModelModule
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
                    appModule,
                    roomModule,
                    networkModule,
                    apiModule,
                    dataModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
