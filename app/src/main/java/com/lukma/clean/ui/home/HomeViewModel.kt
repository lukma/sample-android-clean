package com.lukma.clean.ui.home

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.content.interactor.GetListOfContentUseCase
import com.lukma.clean.ui.common.PagedLiveData

class HomeViewModel(useCase: GetListOfContentUseCase) : ViewModel() {
    internal val liveData = PagedLiveData(useCase::execute)
}
