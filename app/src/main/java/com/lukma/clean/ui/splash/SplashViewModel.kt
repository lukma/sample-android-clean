package com.lukma.clean.ui.splash

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.lukma.clean.domain.auth.interactor.IsAuthenticatedUseCase
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.base.BaseViewModel
import com.lukma.clean.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel(private val isAuthenticatedUseCase: IsAuthenticatedUseCase) : BaseViewModel() {
    internal val launchToNextScreenLiveData = MutableLiveData<Class<out Activity>>()

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
        isAuthenticatedUseCase.execute(onSuccess = {
            val nextScreen = if (it) AuthActivity::class.java else MainActivity::class.java
            launchToNextScreenLiveData.postValue(nextScreen)
        }).addToJob()
    }
}
