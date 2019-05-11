package com.lukma.clean.ui.home

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.content.usecase.GetListOfContentUseCase
import com.lukma.clean.ui.common.PagedBuilder

class HomeViewModel(getListOfContentUseCase: GetListOfContentUseCase) : ViewModel() {
    internal val getListOfContentBuilder = PagedBuilder(getListOfContentUseCase)
}
