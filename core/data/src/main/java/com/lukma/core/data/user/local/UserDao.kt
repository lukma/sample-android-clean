package com.lukma.core.data.user.local

import androidx.room.*

@Dao
interface UserDao {
    @Query(
        """SELECT * FROM users
        WHERE displayName LIKE '%' || :query || '%'
        LIMIT :limit OFFSET :offset"""
    )
    suspend fun finds(query: String, limit: Int, offset: Int): List<UserTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: List<UserTable>)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(users: List<UserTable>) {
        deleteAll()
        insert(users)
    }
}
