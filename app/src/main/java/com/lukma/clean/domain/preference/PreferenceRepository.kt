package com.lukma.clean.domain.preference

import kotlinx.coroutines.Deferred

interface PreferenceRepository {
    fun saveFcmId(value: String): Deferred<Unit>

    fun getFcmId(): Deferred<String>
}
