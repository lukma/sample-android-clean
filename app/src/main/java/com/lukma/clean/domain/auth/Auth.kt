package com.lukma.clean.domain.auth

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auths")
data class Auth(
    @PrimaryKey @NonNull val username: String,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val isActive: Boolean = true
)