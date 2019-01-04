package com.lukma.clean.data.content.entity.table

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contents")
data class ContentTable(
    @PrimaryKey @NonNull val id: String,
    val title: String,
    val thumbnail: String,
    val content: String
)
