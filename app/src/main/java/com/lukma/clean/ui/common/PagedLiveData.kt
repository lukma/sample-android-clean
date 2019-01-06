package com.lukma.clean.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.common.UseCaseConstant

class PagedLiveData<Entity>(private val useCase: UseCase<List<Entity>>, limit: Int = 10) {
    private val pagedDataFactory = Factory()
    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(limit)
        .setPageSize(limit)
        .build()

    internal val state = MutableLiveData<State>()
    internal val data = (LivePagedListBuilder(pagedDataFactory, pagedListConfig)).build()
    internal val error = MutableLiveData<Throwable>()

    fun reload() {
        data.value?.dataSource?.invalidate()
    }

    private inner class Factory : DataSource.Factory<Long, Entity>() {
        override fun create() = object : PageKeyedDataSource<Long, Entity>() {
            override fun loadInitial(
                params: LoadInitialParams<Long>,
                callback: LoadInitialCallback<Long, Entity>
            ) {
                state.postValue(State.ON_INITIAL_REQUEST)
                useCase.execute(
                    params = mapOf(
                        UseCaseConstant.LIMIT to params.requestedLoadSize,
                        UseCaseConstant.OFFSET to 0
                    ),
                    onSuccess = {
                        val next = if (params.requestedLoadSize == it.size) params.requestedLoadSize else null
                        callback.onResult(it, null, next?.toLong())
                    },
                    onError = {
                        callback.onResult(emptyList(), null, null)
                        error.postValue(it)
                    }
                )
            }

            override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Entity>) {
                state.postValue(State.ON_NEXT_REQUEST)
                useCase.execute(
                    params = mapOf(
                        UseCaseConstant.LIMIT to params.requestedLoadSize,
                        UseCaseConstant.OFFSET to 0
                    ),
                    onSuccess = {
                        val next = if (params.requestedLoadSize == it.size) params.requestedLoadSize else null
                        callback.onResult(it, next?.toLong())
                    },
                    onError = {
                        callback.onResult(emptyList(), null)
                        error.postValue(it)
                    }
                )
            }

            override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Entity>) {}
        }
    }

    enum class State {
        NONE,
        ON_INITIAL_REQUEST,
        ON_NEXT_REQUEST,
        ON_SUCCESS,
        ON_FAILURE
    }
}
