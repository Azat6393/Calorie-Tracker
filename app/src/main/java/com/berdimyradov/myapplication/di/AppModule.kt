package com.berdimyradov.myapplication.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.berdimyradov.myapplication.data.local.room.CalorieDao
import com.berdimyradov.myapplication.data.local.room.CalorieTrackerDatabase
import com.berdimyradov.myapplication.data.remote.CalorieApi
import com.berdimyradov.myapplication.data.repository.CalorieRepositoryImpl
import com.berdimyradov.myapplication.domain.repository.CalorieRepository
import com.berdimyradov.myapplication.domain.use_case.*
import com.berdimyradov.myapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCalorieApi(): CalorieApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CalorieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCalorieDatabase(
        app: Application
    ): CalorieTrackerDatabase {
        return Room.databaseBuilder(
            app,
            CalorieTrackerDatabase::class.java,
            "calorie_tracker_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCalorieRepository(
        api: CalorieApi,
        dao: CalorieTrackerDatabase
    ): CalorieRepository {
        return CalorieRepositoryImpl(api, dao.calorieDao)
    }

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                    appContext,
                    "calorie_tracker_preferences"
                )
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile("calorie_tracker_preferences") }
        )
    }

    @Provides
    @Singleton
    fun provideUseCases(
        repo: CalorieRepository,
        dataStore: DataStore<Preferences>
    ): UseCases {
        return UseCases(
            deleteProduct = DeleteProduct(repo),
            getProducts = GetProducts(repo, dataStore),
            insertProduct = InsertProduct(repo),
            searchProduct = SearchProduct(repo),
            deleteAllItem = DeleteAllItem(repo)
        )
    }
}