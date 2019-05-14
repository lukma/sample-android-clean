package com.lukma.clean.domain.common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

    fun toDate(value: String, pattern: String): Date =
        SimpleDateFormat(pattern, Locale.getDefault()).parse(value)

    fun toString(value: Date, pattern: String): String =
        SimpleDateFormat(pattern, Locale.getDefault()).format(value)
}
