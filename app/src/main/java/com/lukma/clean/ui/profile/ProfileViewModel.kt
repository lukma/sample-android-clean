package com.lukma.clean.ui.profile

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.Logout
import com.lukma.clean.ui.common.SingleLiveData

class ProfileViewModel(useCase: Logout) : ViewModel() {
    internal val liveData = SingleLiveData(useCase::execute)

    fun logout() {
        liveData.run()
    }
}