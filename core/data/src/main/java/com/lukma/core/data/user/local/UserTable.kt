package com.lukma.core.data.user.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserTable(
    @PrimaryKey val email: String,
    val displayName: String?,
    val photoUrl: String?
)
