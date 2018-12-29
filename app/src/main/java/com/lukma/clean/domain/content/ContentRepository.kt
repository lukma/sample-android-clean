package com.lukma.clean.domain.content

import kotlinx.coroutines.Deferred

interface ContentRepository {
    fun gets(limit: Int, offset: Int): Deferred<List<Content>>
}