package com.lukma.clean.ui.splash

import androidx.lifecycle.Transformations
import com.lukma.clean.domain.auth.usecase.IsAuthenticatedUseCase
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.SingleLiveData
import com.lukma.clean.ui.common.base.BaseViewModel
import com.lukma.clean.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel(isAuthenticatedUseCase: IsAuthenticatedUseCase) : BaseViewModel() {
    private val isAuthenticatedLiveData = SingleLiveData(isAuthenticatedUseCase)
    internal val launchToNextScreenLiveData = Transformations.map(isAuthenticatedLiveData) {
        if (it) MainActivity::class.java else AuthActivity::class.java
    }

    init {
        delayToNextScreen()
    }

    private fun delayToNextScreen() {
        launch {
            delay(TimeUnit.SECONDS.toSeconds(3))
            isAuthenticated()
        }.runBySupervisor()
    }

    private fun isAuthenticated() {
        isAuthenticatedLiveData.execute().runBySupervisor()
    }
}
