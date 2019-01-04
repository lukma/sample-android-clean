package com.lukma.clean.data.content

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukma.clean.data.content.entity.table.ContentTable

@Dao
interface ContentDao {
    @Query("SELECT * FROM contents LIMIT :limit OFFSET :offset")
    fun gets(limit: Int, offset: Int): List<ContentTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<ContentTable>): List<Long>
}
