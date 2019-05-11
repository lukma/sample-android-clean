package com.lukma.clean.data.content

import com.lukma.clean.data.common.helper.RepositoryHelper.runAsyncIO
import com.lukma.clean.data.content.cloud.ContentApi
import com.lukma.clean.data.content.local.ContentDao
import com.lukma.clean.domain.content.ContentRepository
import java.io.IOException

class ContentDataRepository(
    private val dao: ContentDao,
    private val api: ContentApi
) : ContentRepository {

    override fun gets(limit: Int, offset: Int) = runAsyncIO {
        try {
            val responseApi = api.gets(limit, offset).await().let(ContentMapper::transformToEntity)
            dao.insert(responseApi.let(ContentMapper::transformToTable))
            responseApi
        } catch (ex: IOException) {
            dao.gets(limit, offset).let(ContentMapper::transformToEntity)
        }
    }
}
