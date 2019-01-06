package com.lukma.clean.ui.profile

import com.lukma.clean.domain.auth.interactor.LogoutUseCase
import com.lukma.clean.ui.common.SingleLiveData
import com.lukma.clean.ui.common.base.BaseViewModel

class ProfileViewModel(logoutUseCase: LogoutUseCase) : BaseViewModel() {
    internal val logoutLiveData = SingleLiveData<Unit>(logoutUseCase)

    fun logout() {
        logoutLiveData.execute().addToJob()
    }
}
