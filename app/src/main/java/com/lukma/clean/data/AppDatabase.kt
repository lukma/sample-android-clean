package com.lukma.clean.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukma.clean.data.auth.AuthDao
import com.lukma.clean.data.auth.entity.table.AuthTable
import com.lukma.clean.data.content.ContentDao
import com.lukma.clean.data.content.entity.table.ContentTable

@Database(entities = [(AuthTable::class), (ContentTable::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao

    abstract fun contentDao(): ContentDao
}
