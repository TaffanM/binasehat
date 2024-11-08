package com.mage.binasehat.background.tracking.service.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.mage.binasehat.MainActivity
import com.mage.binasehat.R
import com.mage.binasehat.background.tracking.service.TrackingService
import com.mage.binasehat.ui.util.TimeUtility
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TrackingNotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val TRACKING_NOTIFICATION_CHANNEL_ID = "tracking_notification"
        private const val TRACKING_NOTIFICATION_CHANNEL_NAME = "Run Tracking Status"
        const val TRACKING_NOTIFICATION_ID = 3
    }

    private val intentToRunScreen = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(
            Intent(
                Intent.ACTION_VIEW,
                null,
                context,
                MainActivity::class.java
            )
        )
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)!!
    }

    private val baseNotificationBuilder
        get() = NotificationCompat.Builder(
            context,
            TRACKING_NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.running_boy)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentTitle("Running Time")
            .setContentText("00:00:00")
            .setContentIntent(intentToRunScreen)

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun updateTrackingNotification(durationInMillis: Long, isTracking: Boolean) {
        val notification = baseNotificationBuilder
            .setContentText(TimeUtility.getFormattedStopwatchTime(durationInMillis))
            .clearActions()
            .addAction(getTrackingNotificationAction(isTracking))
            .build()

        notificationManager.notify(TRACKING_NOTIFICATION_ID, notification)
    }

    private fun getTrackingNotificationAction(isTracking: Boolean): NotificationCompat.Action {
        return NotificationCompat.Action(
            if (isTracking) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24,
            if (isTracking) "Pause" else "Resume",
            PendingIntent.getService(
                context,
                2234,
                Intent(
                    context,
                    TrackingService::class.java
                ).apply {
                    action =
                        if (isTracking) TrackingService.ACTION_PAUSE_TRACKING else TrackingService.ACTION_RESUME_TRACKING
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }

    fun removeTrackingNotification() {
        notificationManager.cancel(TRACKING_NOTIFICATION_ID)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return

        val notificationChannel = NotificationChannel(
            TRACKING_NOTIFICATION_CHANNEL_ID,
            TRACKING_NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun getDefaultNotification() = baseNotificationBuilder.build()
}