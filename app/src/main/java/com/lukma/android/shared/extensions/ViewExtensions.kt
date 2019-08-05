package com.lukma.android.shared.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflate(resourceId: Int) =
    LayoutInflater.from(context).inflate(resourceId, this, false)
