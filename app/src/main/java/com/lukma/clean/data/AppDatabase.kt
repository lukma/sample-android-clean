package com.lukma.clean.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lukma.clean.data.auth.local.AuthDao
import com.lukma.clean.data.auth.local.AuthTable
import com.lukma.clean.data.common.converter.DateConverter
import com.lukma.clean.data.content.local.ContentDao
import com.lukma.clean.data.content.local.ContentTable

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
