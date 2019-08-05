package com.lukma.android.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.content.entity.Content
import com.lukma.android.domain.content.usecase.GetListOfContentUseCase
import com.lukma.android.presentation.common.PagedResource
import com.lukma.android.presentation.common.PagedState

class HomeViewModel(private val getListOfContentUseCase: GetListOfContentUseCase) : ViewModel() {
    private val pagedDataFactory = Factory()
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(LIMIT)
        .setPageSize(LIMIT)
        .build()

    private val networkStateMutable = MutableLiveData<PagedResource>()
    internal val networkState: LiveData<PagedResource>
        get() = networkStateMutable

    internal val listOfContent = (LivePagedListBuilder(pagedDataFactory, pagedListConfig)).build()

    fun reload() {
        listOfContent.value?.dataSource?.invalidate()
    }

    companion object {
        private const val LIMIT = 10
    }

    inner class Factory : DataSource.Factory<Long, Content>() {
        override fun create() = ContentListDataSource()
    }

    inner class ContentListDataSource : PageKeyedDataSource<Long, Content>() {
        override fun loadInitial(
            params: LoadInitialParams<Long>,
            callback: LoadInitialCallback<Long, Content>
        ) {
            networkStateMutable.postValue(PagedResource(PagedState.ON_INITIAL_REQUEST))
            getListOfContentUseCase
                .addParams(
                    mapOf(
                        UseCaseConstant.LIMIT to params.requestedLoadSize,
                        UseCaseConstant.OFFSET to 0
                    )
                )
                .onSuccess {
                    val next =
                        if (params.requestedLoadSize == it.size) params.requestedLoadSize else null
                    callback.onResult(it, null, next?.toLong())
                }
                .onError {
                    callback.onResult(emptyList(), null, null)
                    networkStateMutable.postValue(PagedResource(PagedState.ON_FAILURE, it))
                }
                .execute(viewModelScope)
        }

        override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Content>) {
            networkStateMutable.postValue(PagedResource(PagedState.ON_NEXT_REQUEST))
            getListOfContentUseCase
                .addParams(
                    mapOf(
                        UseCaseConstant.LIMIT to params.requestedLoadSize,
                        UseCaseConstant.OFFSET to params.key.toInt()
                    )
                )
                .onSuccess {
                    val next =
                        if (params.requestedLoadSize == it.size) params.requestedLoadSize else null
                    callback.onResult(it, next?.toLong())
                }
                .onError {
                    callback.onResult(emptyList(), null)
                    networkStateMutable.postValue(PagedResource(PagedState.ON_FAILURE, it))
                }
                .execute(viewModelScope)
        }

        override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Content>) {}
    }
}
