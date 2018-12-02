package com.lukma.clean.ui.main

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.IsAuthenticated
import com.lukma.clean.ui.common.SingleLiveData

class MainViewModel(private val useCase: IsAuthenticated) : ViewModel() {
    internal val liveData = SingleLiveData(useCase::execute)

    init {
        liveData.run()
    }

    override fun onCleared() {
        useCase.dispose()
    }
}