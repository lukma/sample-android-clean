package com.lukma.clean.ui.home

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.content.interactor.GetContents
import com.lukma.clean.ui.common.PagedFetchData

class HomeViewModel(private val useCase: GetContents) : ViewModel() {
    val fetchData = PagedFetchData(useCase::execute)

    override fun onCleared() {
        useCase.dispose()
    }
}