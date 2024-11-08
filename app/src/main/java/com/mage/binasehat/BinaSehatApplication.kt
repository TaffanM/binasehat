package com.mage.binasehat

import android.app.Application
import com.mage.binasehat.background.tracking.service.notification.TrackingNotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BinaSehatApplication: Application() {
    @Inject
    lateinit var notificationHelper: TrackingNotificationHelper
    override fun onCreate() {
        super.onCreate()
        notificationHelper.createNotificationChannel()
    }
}