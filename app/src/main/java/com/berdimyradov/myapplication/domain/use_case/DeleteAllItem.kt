package com.berdimyradov.myapplication.domain.use_case

import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import javax.inject.Inject

class DeleteAllItem @Inject constructor(
    private val repo: CalorieRepository
) {
    suspend operator fun invoke() = repo.deleteAllItems()
}