package com.lukma.android.presentation.common.widget.recycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lukma.android.domain.common.entity.Either

class PagedFactory<T>(private val onRequest: suspend (Int?) -> Either<Throwable, List<T>>) :
    DataSource.Factory<Int, T>() {

    private val pagedStateMutable = MutableLiveData<PagedState>()
    internal val pagedState: LiveData<PagedState>
        get() = pagedStateMutable

    override fun create() = PagedDataSource {
        val isInitial = it == null
        pagedStateMutable.postValue(
            PagedState.Loading(
                isInitial
            )
        )
        handleRequest(isInitial, onRequest(it))
    }

    private fun handleRequest(isInitial: Boolean, data: Either<Throwable, List<T>>) = when (data) {
        is Either.Value -> {
            pagedStateMutable.postValue(
                PagedState.Success(
                    isInitial
                )
            )
            data.value
        }
        is Either.Error -> {
            pagedStateMutable.postValue(
                PagedState.Failure(
                    isInitial,
                    data.error
                )
            )
            emptyList()
        }
    }
}
