package com.lukma.clean.ui.splash

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.IsAuthenticated
import com.lukma.clean.ui.common.SingleLiveData

class SplashViewModel(isAuthenticatedUseCase: IsAuthenticated) : ViewModel() {
    internal val isAuthenticatedLiveData = SingleLiveData(isAuthenticatedUseCase::execute)

    fun isAuthenticated() {
        isAuthenticatedLiveData.run()
    }
}
