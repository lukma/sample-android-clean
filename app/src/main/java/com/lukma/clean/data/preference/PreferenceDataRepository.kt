package com.lukma.clean.data.preference

import android.content.SharedPreferences
import com.lukma.clean.domain.preference.PreferenceRepository
import com.lukma.clean.extensions.toDeferred

class PreferenceDataRepository(private val sharedPreferences: SharedPreferences) :
    PreferenceRepository {

    override suspend fun saveFcmId(value: String) =
        sharedPreferences.edit().putString(KEY_FCM_ID, value).apply().toDeferred()

    override suspend fun getFcmId() =
        sharedPreferences.getString(KEY_FCM_ID, null).orEmpty().toDeferred()

    companion object {
        private const val KEY_FCM_ID = "KEY_FCM_ID"
    }
}
