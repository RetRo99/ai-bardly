package com.retro99.database.implementation.dao.shelfs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShelfsDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(item: RoomShelfEntity)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(items: List<RoomShelfEntity>)

    @Query("SELECT * FROM RoomShelfEntity WHERE id = :id")
    suspend fun getShelf(id: Int): RoomShelfEntity

    @Query("SELECT * FROM RoomShelfEntity")
    suspend fun getShelfs(): List<RoomShelfEntity>
}