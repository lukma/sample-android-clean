package com.lukma.clean.domain.preference

import kotlinx.coroutines.Deferred

interface PreferenceRepository {
    suspend fun saveFcmId(value: String): Deferred<Unit>

    suspend fun getFcmId(): Deferred<String>
}
