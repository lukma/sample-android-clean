package com.lukma.clean.data.content.store

import com.lukma.clean.data.common.DataStoreType
import com.lukma.clean.data.content.store.cloud.ContentApi
import com.lukma.clean.data.content.store.cloud.ContentCloudStore
import com.lukma.clean.data.content.store.database.ContentDao
import com.lukma.clean.data.content.store.database.ContentDatabaseStore

class ContentStoreFactory(
        private val api: ContentApi,
        private val dao: ContentDao,
        private val mapper: ContentMapper
) {
    fun createData(type: DataStoreType = DataStoreType.CLOUD) = when (type) {
        DataStoreType.CLOUD -> ContentCloudStore(api, mapper)
        DataStoreType.DATABASE -> ContentDatabaseStore(dao, mapper)
    }
}