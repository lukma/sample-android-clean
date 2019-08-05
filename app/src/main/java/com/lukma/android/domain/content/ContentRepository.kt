package com.lukma.android.domain.content

import com.lukma.android.domain.content.entity.Content

interface ContentRepository {
    suspend fun gets(limit: Int, offset: Int): List<Content>
}
