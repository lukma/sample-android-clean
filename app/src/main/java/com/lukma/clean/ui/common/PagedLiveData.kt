package com.lukma.clean.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.lukma.clean.domain.common.UseCaseConstant
import kotlinx.coroutines.Job

class PagedLiveData<Entity>(
    onRunning: (
        params: Map<String, Any?>,
        onSuccess: (List<Entity>) -> Unit,
        onError: (Throwable) -> Unit
    ) -> Job,
    limit: Int = 10,
    start: Int = 0
) {
    val state = MutableLiveData<State>()
    val data: LiveData<PagedList<Entity>>
    val error = MutableLiveData<Throwable>()

    init {
        val pagedDataFactory = PagedDataFactory(onRunning, start, state, error)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(limit)
            .setPageSize(limit)
            .build()
        data = (LivePagedListBuilder(pagedDataFactory, pagedListConfig)).build()
    }

    fun reload() {
        data.value?.dataSource?.invalidate()
    }

    class PagedDataFactory<Entity>(
        private val onRunning: (
            params: Map<String, Any?>,
            onSuccess: (List<Entity>) -> Unit,
            onError: (Throwable) -> Unit
        ) -> Job,
        val start: Int,
        private val state: MutableLiveData<State>,
        val error: MutableLiveData<Throwable>
    ) : DataSource.Factory<Long, Entity>() {
        override fun create() = PagedDataSource(onRunning, start, state, error)
    }

    class PagedDataSource<Entity>(
        val onRunning: (
            params: Map<String, Any?>,
            onSuccess: (List<Entity>) -> Unit,
            onError: (Throwable) -> Unit
        ) -> Job,
        val start: Int,
        val state: MutableLiveData<State>,
        val error: MutableLiveData<Throwable>
    ) : PageKeyedDataSource<Long, Entity>() {
        override fun loadInitial(
            params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Entity>
        ) {
            state.postValue(State.ON_FIRST_REQUEST)
            run(mapOf(
                UseCaseConstant.LIMIT to params.requestedLoadSize,
                UseCaseConstant.OFFSET to start
            )) {
                callback.onResult(it, null, if (it.size == params.requestedLoadSize)
                    params.requestedLoadSize.toLong() else null)
            }
        }

        override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Entity>) {
            state.postValue(State.ON_NEXT_REQUEST)
            run(mapOf(
                UseCaseConstant.LIMIT to params.requestedLoadSize,
                UseCaseConstant.OFFSET to params.key.toInt()
            )) {
                callback.onResult(it, if (it.size == params.requestedLoadSize)
                    params.key.plus(params.requestedLoadSize) else null)
            }
        }

        override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Entity>) {}

        private fun run(params: Map<String, Any?>, onReceive: (data: List<Entity>) -> Unit) {
            onRunning(
                params,
                {
                    state.postValue(State.ON_SUCCESS)
                    onReceive(it)
                },
                {
                    state.postValue(State.ON_FAILURE)
                    error.postValue(it)
                }
            )
        }
    }

    data class Resource<Entity>(
        val state: State = State.NONE,
        val data: Entity? = null,
        val error: Throwable? = null
    )

    enum class State {
        NONE,
        ON_FIRST_REQUEST,
        ON_NEXT_REQUEST,
        ON_SUCCESS,
        ON_FAILURE
    }
}