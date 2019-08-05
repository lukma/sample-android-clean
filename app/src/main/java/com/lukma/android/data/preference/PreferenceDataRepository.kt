package com.lukma.android.data.preference

import android.content.SharedPreferences
import com.lukma.android.domain.preference.PreferenceRepository

class PreferenceDataRepository(private val sharedPreferences: SharedPreferences) :
    PreferenceRepository {

    override suspend fun saveFcmId(value: String) =
        sharedPreferences.edit().putString(KEY_FCM_ID, value).apply()

    override suspend fun getFcmId() =
        sharedPreferences.getString(KEY_FCM_ID, null).orEmpty()

    companion object {
        private const val KEY_FCM_ID = "KEY_FCM_ID"
    }
}
