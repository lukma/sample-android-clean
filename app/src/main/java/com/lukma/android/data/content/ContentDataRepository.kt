package com.lukma.android.data.content

import com.lukma.android.data.common.exception.repo.NotFoundException
import com.lukma.android.data.content.cloud.ContentApi
import com.lukma.android.data.content.local.ContentDao
import com.lukma.android.domain.content.ContentRepository
import com.lukma.android.domain.content.entity.Content

class ContentDataRepository(private val dao: ContentDao, private val api: ContentApi) :
    ContentRepository {

    override suspend fun gets(limit: Int, offset: Int): List<Content> = runCatching {
        api.gets(limit, offset).data?.map(::transform)?.also { dao.insert(it.map(::transform)) }
            ?: throw NotFoundException()
    }.getOrElse {
        dao.gets(limit, offset).map(::transform)
    }
}
