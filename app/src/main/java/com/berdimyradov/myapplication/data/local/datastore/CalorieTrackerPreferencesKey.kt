package com.berdimyradov.myapplication.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object CalorieTrackerPreferencesKey {
    val LAST_SAVED_DATE = stringPreferencesKey( "last_saved_date")
}