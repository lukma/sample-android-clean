package com.lukma.clean.data.auth.local

import androidx.room.*

@Dao
interface AuthDao {
    @Query("SELECT * FROM auths WHERE username = :username LIMIT 1")
    fun get(username: String): AuthTable

    @Query("SELECT * FROM auths WHERE isActive = 1 LIMIT 1")
    fun getIsActive(): AuthTable?

    @Query("SELECT * FROM auths")
    fun gets(): List<AuthTable>

    @Query("SELECT COUNT(*) FROM auths")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: AuthTable): Long

    @Update
    fun update(data: AuthTable): Int

    @Delete
    fun delete(data: AuthTable): Int
}
