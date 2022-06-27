package com.berdimyradov.myapplication.domain.repository

import com.berdimyradov.myapplication.domain.model.CalorieResponse
import com.berdimyradov.myapplication.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface CalorieRepository {

    suspend fun searchForProduct(query: String): CalorieResponse

    suspend fun addItem(item: Item)

    suspend fun deleteItem(item: Item)

    suspend fun getAllItems(): List<Item>

    suspend fun deleteAllItems()

}