package com.berdimyradov.myapplication.domain.use_case

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.berdimyradov.myapplication.data.local.datastore.CalorieTrackerDate
import com.berdimyradov.myapplication.data.local.datastore.CalorieTrackerPreferencesKey
import com.berdimyradov.myapplication.domain.model.Item
import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import com.berdimyradov.myapplication.utils.getCurrentDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val repo: CalorieRepository,
    private val dataStore: DataStore<Preferences>
) {
    suspend operator fun invoke(): Flow<List<Item>> = flow {
        println("Getting")
        emit(repo.getAllItems())

        val calorieTrackerDate = dataStore.data.map { preferences ->
            val date = preferences[CalorieTrackerPreferencesKey.LAST_SAVED_DATE]
                ?: getCurrentDate()
            CalorieTrackerDate(last_saved_date = date)
        }
        if (calorieTrackerDate.last().last_saved_date != getCurrentDate()) {
            repo.deleteAllItems()
        }
    }
}