package com.lukma.clean.data.auth.store.database

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface AuthDao {
    @Query("SELECT * FROM auths WHERE username = :username LIMIT 1")
    fun get(username: String): Flowable<AuthTable>

    @Query("SELECT * FROM auths WHERE isActive = 1 LIMIT 1")
    fun getIsActive(): Flowable<AuthTable>

    @Query("SELECT * FROM auths")
    fun gets(): Flowable<List<AuthTable>>

    @Query("SELECT COUNT(*) FROM auths")
    fun count(): Flowable<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: AuthTable): Long

    @Update
    fun update(data: AuthTable): Int

    @Delete
    fun delete(data: AuthTable): Int
}