package com.lukma.android.data.content.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentDao {
    @Query("SELECT * FROM contents LIMIT :limit OFFSET :offset")
    suspend fun gets(limit: Int, offset: Int): List<ContentTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<ContentTable>): List<Long>
}
