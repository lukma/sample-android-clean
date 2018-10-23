package com.lukma.clean.data.content.store.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface ContentDao {
    @Query("SELECT * FROM contents LIMIT :limit OFFSET :offset")
    fun gets(limit: Int, offset: Int): Flowable<List<ContentTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: List<ContentTable>): List<Long>
}