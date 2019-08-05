package com.lukma.android.shared.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resourceId: Int): View =
    LayoutInflater.from(context).inflate(resourceId, this, false)
