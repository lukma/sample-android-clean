package com.lukma.clean.data.common.converter

import androidx.room.TypeConverter
import com.lukma.clean.shared.utils.DateUtils
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromJson(value: String?) = value?.let { DateUtils.toDate(it, DateUtils.TIMESTAMP_FORMAT) }

    @TypeConverter
    fun toJson(value: Date?) = value?.let { DateUtils.toString(it, DateUtils.TIMESTAMP_FORMAT) }
}
