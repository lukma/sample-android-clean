package com.lukma.clean.ui.profile

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.Logout
import com.lukma.clean.ui.common.SingleLiveData

class ProfileViewModel(private val useCase: Logout) : ViewModel() {
    private val liveData = SingleLiveData(useCase::execute)

    fun logout() {
        liveData.run()
    }

    override fun onCleared() {
        useCase.dispose()
    }
}