package com.lukma.clean.domain.content

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contents")
data class Content(
    @PrimaryKey @NonNull val id: String,
    val title: String,
    val thumbnail: String,
    val content: String
)