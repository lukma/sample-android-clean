package com.lukma.clean.data.common

import android.content.Context
import com.lukma.clean.BuildConfig

class SessionManager(context: Context) {
    companion object {
        private const val KEY_FCM_ID = "KEY_FCM_ID"
    }

    private val pref = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    fun saveFcmId(value: String?) = pref.edit().putString(KEY_FCM_ID, value).commit()

    fun getFcmId() = pref.getString(KEY_FCM_ID, null).orEmpty()
}