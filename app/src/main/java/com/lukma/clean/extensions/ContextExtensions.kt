package com.lukma.clean.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

fun Context.startActivityClearTask(clazz: Class<out Activity>) {
    startActivity(
        Intent(this, clazz)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    )
}
