package com.lukma.android.presentation.common.widget.recycler

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PagedDataSource<T>(private val onRequest: suspend (Int?) -> List<T>) :
    PageKeyedDataSource<Int, T>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        launch {
            val data = onRequest(null)
            val next = if (params.requestedLoadSize == data.size) 2 else null
            callback.onResult(data, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        launch {
            val data = onRequest(params.key)
            val next = if (params.requestedLoadSize == data.size) params.key + 1 else null
            callback.onResult(data, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}
}
