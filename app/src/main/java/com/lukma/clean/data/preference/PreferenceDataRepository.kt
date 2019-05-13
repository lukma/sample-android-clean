package com.lukma.clean.data.preference

import android.content.Context
import com.lukma.clean.BuildConfig
import com.lukma.clean.domain.preference.PreferenceRepository
import com.lukma.clean.extensions.toDeferred

class PreferenceDataRepository(context: Context) : PreferenceRepository {
    private val pref =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    override suspend fun saveFcmId(value: String) =
        pref.edit().putString(KEY_FCM_ID, value).apply().toDeferred()

    override suspend fun getFcmId() = pref.getString(KEY_FCM_ID, null).orEmpty().toDeferred()

    companion object {
        private const val KEY_FCM_ID = "KEY_FCM_ID"
    }
}
