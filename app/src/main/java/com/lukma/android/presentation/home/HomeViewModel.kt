package com.lukma.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.common.entity.Either
import com.lukma.android.domain.content.entity.Content
import com.lukma.android.domain.content.usecase.GetListOfContentUseCase
import com.lukma.android.presentation.common.widget.recycler.PagedFactory
import com.lukma.android.presentation.common.widget.recycler.PagedState

class HomeViewModel(private val getListOfContentUseCase: GetListOfContentUseCase) : ViewModel() {
    private val pagedDataFactory = PagedFactory(::getListOfContent)
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(1)
        .setPageSize(10)
        .build()
    val contents = (LivePagedListBuilder(pagedDataFactory, pagedListConfig)).build()
    val networkState: LiveData<PagedState> = pagedDataFactory.pagedState

    private suspend fun getListOfContent(offset: Int? = 0): Either<Throwable, List<Content>> {
        val params = mapOf(
            UseCaseConstant.LIMIT to LIMIT,
            UseCaseConstant.OFFSET to offset
        )
        return getListOfContentUseCase.addParams(params).invoke()
    }

    companion object {
        private const val LIMIT = 10
    }
}
