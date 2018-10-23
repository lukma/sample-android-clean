package com.lukma.clean.data.auth.store

import com.lukma.clean.data.auth.store.cloud.AuthApi
import com.lukma.clean.data.auth.store.cloud.AuthCloudStore
import com.lukma.clean.data.auth.store.database.AuthDao
import com.lukma.clean.data.auth.store.database.AuthDatabaseStore
import com.lukma.clean.data.common.DataStoreType

class AuthStoreFactory(
        private val api: AuthApi,
        private val dao: AuthDao,
        private val mapper: AuthMapper
) {
    fun createData(type: DataStoreType = DataStoreType.CLOUD) = when (type) {
        DataStoreType.CLOUD -> AuthCloudStore(api, mapper)
        DataStoreType.DATABASE -> AuthDatabaseStore(dao, mapper)
    }
}