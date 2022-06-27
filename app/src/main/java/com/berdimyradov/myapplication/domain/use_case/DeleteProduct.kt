package com.berdimyradov.myapplication.domain.use_case

import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import javax.inject.Inject

class DeleteProduct @Inject constructor(
    private val repo: CalorieRepository
) {
    suspend operator fun invoke(item: Item) = repo.deleteItem(item)
}