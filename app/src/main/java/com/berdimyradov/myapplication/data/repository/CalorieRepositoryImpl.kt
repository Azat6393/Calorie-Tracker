package com.berdimyradov.myapplication.data.repository

import com.berdimyradov.myapplication.data.local.room.CalorieDao
import com.berdimyradov.myapplication.data.local.room.CalorieTrackerDatabase
import com.berdimyradov.myapplication.data.remote.CalorieApi
import com.berdimyradov.myapplication.domain.model.CalorieResponse
import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CalorieRepositoryImpl @Inject constructor(
    private val calorieApi: CalorieApi,
    private val dao: CalorieDao
) : CalorieRepository {

    override suspend fun searchForProduct(query: String): CalorieResponse {
        return calorieApi.searchForProduct(query = query)
    }

    override suspend fun addItem(item: Item) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: Item) {
        dao.deleteItem(item)
    }

    override suspend fun getAllItems(): List<Item> {
        return dao.getAllItems()
    }

    override suspend fun deleteAllItems() {
        dao.deleteAllItems()
    }
}