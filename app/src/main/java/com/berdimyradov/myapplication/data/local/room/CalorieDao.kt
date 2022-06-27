package com.berdimyradov.myapplication.data.local.room

import androidx.room.*
import com.berdimyradov.myapplication.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface CalorieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM item")
    suspend fun getAllItems(): List<Item>

    @Query("DELETE FROM item")
    suspend fun deleteAllItems()
}