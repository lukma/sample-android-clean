package com.lukma.android.presentation.splash

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.lukma.android.domain.auth.usecase.IsAuthenticatedUseCase
import com.lukma.android.presentation.auth.AuthActivity
import com.lukma.android.presentation.common.base.BaseViewModel
import com.lukma.android.presentation.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel(private val isAuthenticatedUseCase: IsAuthenticatedUseCase) :
    BaseViewModel() {

    private val isAuthenticated = MutableLiveData<Boolean>()
    internal val launchToNextScreenAction: LiveData<Class<out Activity>>
        get() = Transformations.map(isAuthenticated) {
            if (it) MainActivity::class.java else AuthActivity::class.java
        }

    fun delayToNextScreen() {
        viewModelScope.launch {
            delay(TimeUnit.SECONDS.toSeconds(3))
            isAuthenticated()
        }
    }

    private fun isAuthenticated() {
        isAuthenticatedUseCase.onSuccess(isAuthenticated::postValue).execute()
    }
}