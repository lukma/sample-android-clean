package com.lukma.clean.ui.profile

import androidx.lifecycle.MutableLiveData
import com.lukma.clean.domain.auth.interactor.LogoutUseCase
import com.lukma.clean.ui.common.base.BaseViewModel

class ProfileViewModel(private val logoutUseCase: LogoutUseCase) : BaseViewModel() {
    internal val logoutLiveData = MutableLiveData<Unit>()

    fun logout() {
        logoutUseCase.execute(onSuccess = {
            logoutLiveData.postValue(Unit)
        }).addToJob()
    }
}
