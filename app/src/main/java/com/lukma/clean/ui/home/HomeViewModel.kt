package com.lukma.clean.ui.home

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.content.interactor.GetContents
import com.lukma.clean.ui.common.PagedLiveData

class HomeViewModel(private val useCase: GetContents) : ViewModel() {
    internal val liveData = PagedLiveData(useCase::execute)

    override fun onCleared() {
        useCase.dispose()
    }
}