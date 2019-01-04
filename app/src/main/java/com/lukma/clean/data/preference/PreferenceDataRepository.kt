package com.lukma.clean.data.preference

import android.content.Context
import com.lukma.clean.BuildConfig
import com.lukma.clean.data.common.helper.RepositoryHelper.runAsyncIO
import com.lukma.clean.domain.preference.PreferenceRepository

class PreferenceDataRepository(context: Context) : PreferenceRepository {
    companion object {
        private const val KEY_FCM_ID = "KEY_FCM_ID"
    }

    private val pref = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    override fun saveFcmId(value: String) = runAsyncIO {
        pref.edit().putString(KEY_FCM_ID, value).apply()
    }

    override fun getFcmId() = runAsyncIO {
        pref.getString(KEY_FCM_ID, null).orEmpty()
    }
}
