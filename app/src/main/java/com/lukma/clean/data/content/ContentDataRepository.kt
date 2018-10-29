package com.lukma.clean.data.content

import com.lukma.clean.data.common.DataStoreType
import com.lukma.clean.data.common.ListParams
import com.lukma.clean.data.content.store.ContentStoreFactory
import com.lukma.clean.domain.content.Content
import com.lukma.clean.domain.content.ContentRepository

class ContentDataRepository(private val store: ContentStoreFactory) : ContentRepository {
    override fun gets(params: ListParams) = store
            .createData()
            .gets(params)
            .doOnNext { insert(it) }
            .onErrorResumeNext(store.createData(DataStoreType.DATABASE).gets(params))

    override fun insert(data: List<Content>) = store
            .createData(DataStoreType.DATABASE)
            .insert(data)
}