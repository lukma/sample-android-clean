package com.lukma.clean.ui.splash

import android.app.Activity
import com.lukma.clean.domain.auth.interactor.IsAuthenticatedUseCase
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.SingleLiveData
import com.lukma.clean.ui.common.base.BaseViewModel
import com.lukma.clean.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel(isAuthenticatedUseCase: IsAuthenticatedUseCase) : BaseViewModel() {
    internal val launchToNextScreenLiveData = SingleLiveData<Class<out Activity>>(isAuthenticatedUseCase)

    init {
        delayToNextScreen()
    }

    private fun delayToNextScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(TimeUnit.SECONDS.toSeconds(3))
            isAuthenticated()
        }.addToJob()
    }

    private fun isAuthenticated() {
        launchToNextScreenLiveData.execute {
            if (it as Boolean) MainActivity::class.java else AuthActivity::class.java
        }.addToJob()
    }
}
