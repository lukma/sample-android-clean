package com.lukma.clean.data.content

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lukma.clean.domain.content.Content

@Dao
interface ContentDao {
    @Query("SELECT * FROM contents LIMIT :limit OFFSET :offset")
    fun gets(limit: Int, offset: Int): List<Content>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<Content>): List<Long>
}