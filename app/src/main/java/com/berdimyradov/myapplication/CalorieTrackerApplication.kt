package com.berdimyradov.myapplication

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CalorieTrackerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        OneSignal.initWithContext(this)
        OneSignal.setAppId("ONESIGNAL_APP_ID")

    }

}