package com.lukma.clean.data.content

import com.lukma.clean.data.common.DataStoreType
import com.lukma.clean.data.common.ListParams
import com.lukma.clean.data.content.store.ContentStoreFactory

class ContentRepositoryImpl(private val store: ContentStoreFactory) : ContentRepository {
    override fun gets(params: ListParams) = store
            .createData()
            .gets(params)
            .doOnNext { insert(it) }
            .onErrorResumeNext(store.createData(DataStoreType.DATABASE).gets(params))

    override fun insert(data: List<Content>) = store
            .createData(DataStoreType.DATABASE)
            .insert(data)
}