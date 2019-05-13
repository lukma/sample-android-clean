package com.lukma.clean.data.content

import com.lukma.clean.data.content.cloud.ContentApi
import com.lukma.clean.data.content.local.ContentDao
import com.lukma.clean.domain.content.ContentRepository
import com.lukma.clean.extensions.runAsync
import java.io.IOException

class ContentDataRepository(private val dao: ContentDao, private val api: ContentApi) :
    ContentRepository {

    override suspend fun gets(limit: Int, offset: Int) = runAsync {
        try {
            val responseApi = api.gets(limit, offset).await().let(ContentMapper::transformToEntity)
            dao.insert(responseApi.let(ContentMapper::transformToTable))
            responseApi
        } catch (ex: IOException) {
            dao.gets(limit, offset).let(ContentMapper::transformToEntity)
        }
    }
}
