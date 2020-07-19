package com.lukma.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukma.core.data.user.local.UserDao
import com.lukma.core.data.user.local.UserTable

@Database(entities = [UserTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
