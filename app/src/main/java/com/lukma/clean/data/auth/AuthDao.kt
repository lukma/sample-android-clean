package com.lukma.clean.data.auth

import androidx.room.*
import com.lukma.clean.domain.auth.Auth

@Dao
interface AuthDao {
    @Query("SELECT * FROM auths WHERE username = :username LIMIT 1")
    fun get(username: String): Auth

    @Query("SELECT * FROM auths WHERE isActive = 1 LIMIT 1")
    fun getIsActive(): Auth

    @Query("SELECT * FROM auths")
    fun gets(): List<Auth>

    @Query("SELECT COUNT(*) FROM auths")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Auth): Long

    @Update
    fun update(data: Auth): Int

    @Delete
    fun delete(data: Auth): Int
}