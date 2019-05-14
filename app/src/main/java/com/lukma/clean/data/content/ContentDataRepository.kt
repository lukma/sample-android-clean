package com.lukma.clean.data.content

import com.lukma.clean.data.content.cloud.ContentApi
import com.lukma.clean.data.content.local.ContentDao
import com.lukma.clean.domain.content.ContentRepository
import com.lukma.clean.extensions.runAsync
import org.koin.core.error.MissingPropertyException
import java.io.IOException

class ContentDataRepository(private val dao: ContentDao, private val api: ContentApi) :
    ContentRepository {

    override suspend fun gets(limit: Int, offset: Int) = runAsync {
        try {
            val response = api.gets(limit, offset).await().data?.map(::transform)
                ?: throw MissingPropertyException("data")
            dao.insert(response.map(::transform))
            response
        } catch (ex: IOException) {
            dao.gets(limit, offset).map(::transform)
        }
    }
}
