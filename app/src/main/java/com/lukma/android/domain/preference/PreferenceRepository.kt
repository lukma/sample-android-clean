package com.lukma.android.domain.preference

interface PreferenceRepository {
    suspend fun saveFcmId(value: String)

    suspend fun getFcmId(): String
}
