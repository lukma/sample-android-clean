package com.lukma.clean.data.content.store.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contents")
data class ContentTable(
        @PrimaryKey
        @NonNull
        val id: String,
        val title: String,
        val thumbnail: String,
        val content: String
)