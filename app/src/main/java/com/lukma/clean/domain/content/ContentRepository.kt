package com.lukma.clean.domain.content

import com.lukma.clean.domain.content.entity.Content

interface ContentRepository {
    suspend fun gets(limit: Int, offset: Int): List<Content>
}
