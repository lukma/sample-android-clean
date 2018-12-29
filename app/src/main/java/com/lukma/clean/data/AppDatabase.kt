package com.lukma.clean.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lukma.clean.data.auth.AuthDao
import com.lukma.clean.data.content.ContentDao
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.content.Content

@Database(entities = [(Auth::class), (Content::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao

    abstract fun contentDao(): ContentDao
}