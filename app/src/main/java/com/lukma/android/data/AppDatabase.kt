package com.lukma.android.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lukma.android.data.auth.local.AuthDao
import com.lukma.android.data.auth.local.AuthTable
import com.lukma.android.data.common.converter.DateConverter
import com.lukma.android.data.content.local.ContentDao
import com.lukma.android.data.content.local.ContentTable

@Database(
    entities = [
        AuthTable::class,
        ContentTable::class
    ],
    version = 1
)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao

    abstract fun contentDao(): ContentDao
}
