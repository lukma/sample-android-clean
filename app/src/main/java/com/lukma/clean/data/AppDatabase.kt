package com.lukma.clean.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukma.clean.data.auth.store.database.AuthDao
import com.lukma.clean.data.auth.store.database.AuthTable
import com.lukma.clean.data.content.store.database.ContentDao
import com.lukma.clean.data.content.store.database.ContentTable

@Database(entities = [(AuthTable::class), (ContentTable::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao

    abstract fun contentDao(): ContentDao
}