package com.lukma.android.data.auth.local

import androidx.room.*

@Dao
interface AuthDao {
    @Query("SELECT * FROM auths WHERE username = :username LIMIT 1")
    suspend fun get(username: String): AuthTable?

    @Query("SELECT * FROM auths WHERE isActive = 1 LIMIT 1")
    suspend fun getIsActive(): AuthTable?

    @Query("SELECT * FROM auths")
    suspend fun gets(): List<AuthTable>

    @Query("SELECT COUNT(*) FROM auths")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: AuthTable)

    @Update
    suspend fun update(data: AuthTable)

    @Delete
    suspend fun delete(data: AuthTable)
}
