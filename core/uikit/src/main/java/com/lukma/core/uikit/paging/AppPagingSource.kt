package com.lukma.core.uikit.paging

import androidx.paging.PagingSource

class AppPagingSource<T : Any>(
    private val onLoadData: suspend (LoadParams<Int>) -> List<T>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val data = onLoadData(params)
            val pageNumber = params.key ?: 0
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (data.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(data, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
