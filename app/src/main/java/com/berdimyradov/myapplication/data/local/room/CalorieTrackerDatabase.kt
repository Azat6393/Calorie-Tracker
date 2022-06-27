package com.berdimyradov.myapplication.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.berdimyradov.myapplication.domain.model.Item

@Database(
    entities = [Item::class],
    version = 1,
)
abstract class CalorieTrackerDatabase : RoomDatabase() {
    abstract val calorieDao: CalorieDao
}