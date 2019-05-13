package com.lukma.clean.domain.content

import com.lukma.clean.domain.content.entity.Content
import kotlinx.coroutines.Deferred

interface ContentRepository {
    suspend fun gets(limit: Int, offset: Int): Deferred<List<Content>>
}
