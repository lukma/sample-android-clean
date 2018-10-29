package com.lukma.clean.ui.main

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.IsAuthenticated
import com.lukma.clean.ui.common.SingleFetchData

class MainViewModel(private val useCase: IsAuthenticated) : ViewModel() {
    val fetchData = SingleFetchData(useCase::execute)

    init {
        fetchData.run()
    }

    override fun onCleared() {
        useCase.dispose()
    }
}