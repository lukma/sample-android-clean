package com.lukma.clean.data.content.store.cloud

import com.lukma.clean.data.common.ListParams
import com.lukma.clean.data.content.Content
import com.lukma.clean.data.content.ContentRepository
import com.lukma.clean.data.content.store.ContentMapper

class ContentCloudStore(private val api: ContentApi, private val mapper: ContentMapper)
    : ContentRepository {

    override fun gets(params: ListParams) = api
            .gets(params.limit, params.offset)
            .map(mapper::transform)

    override fun insert(data: List<Content>) = throw IllegalAccessException()
}