package com.lukma.clean.data.auth.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auths")
data class AuthTable(
    @PrimaryKey @NonNull val username: String,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val isActive: Boolean = true
)
