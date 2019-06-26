package com.lukma.clean.data.content

import com.lukma.clean.data.common.exception.repo.NotFoundException
import com.lukma.clean.data.content.cloud.ContentApi
import com.lukma.clean.data.content.local.ContentDao
import com.lukma.clean.domain.content.ContentRepository
import com.lukma.clean.domain.content.entity.Content
import java.io.IOException

class ContentDataRepository(private val dao: ContentDao, private val api: ContentApi) :
    ContentRepository {

    override suspend fun gets(limit: Int, offset: Int): List<Content> {
        return try {
            val response = api.gets(limit, offset).await().data?.map(::transform)
                ?: throw NotFoundException()
            dao.insert(response.map(::transform))
            response
        } catch (ex: IOException) {
            dao.gets(limit, offset).map(::transform)
        }
    }
}
