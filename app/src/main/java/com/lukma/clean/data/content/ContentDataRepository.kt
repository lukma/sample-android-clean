package com.lukma.clean.data.content

import com.lukma.clean.data.common.helper.RepositoryHelper.runAsyncIO
import com.lukma.clean.domain.content.ContentRepository

class ContentDataRepository(
    private val dao: ContentDao,
    private val api: ContentApi
) : ContentRepository {
    override fun gets(limit: Int, offset: Int) = runAsyncIO {
        try {
            val responseApi = api.gets(limit, offset).await().let(ContentMapper::transform)
            dao.insert(responseApi.let(ContentMapper::transform))
        } catch (ex: Exception) {
        }
        api.gets(limit, offset).await().let(ContentMapper::transform)
    }
}
