package com.lukma.clean.data.content.store.database

import com.lukma.clean.data.common.ListParams
import com.lukma.clean.data.content.store.ContentMapper
import com.lukma.clean.domain.content.Content
import com.lukma.clean.domain.content.ContentRepository
import io.reactivex.Flowable

class ContentDatabaseStore(private val dao: ContentDao, private val mapper: ContentMapper)
    : ContentRepository {

    override fun gets(params: ListParams) = dao
            .gets(params.limit, params.offset)
            .map(mapper::transform)

    override fun insert(data: List<Content>) = Flowable
            .just(dao.insert(data.map(mapper::transform)))
            .map { it.last().toInt() == 1 }
}