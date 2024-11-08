package com.mage.binasehat.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mage.binasehat.R
import java.time.LocalTime
import java.util.concurrent.TimeUnit

object TimeUtility {
    @Composable
    fun getGreeting(): String {
        val currentHour = LocalTime.now().hour
        return when (
            currentHour) {
            in 0..11 -> stringResource(R.string.greeting_morning)
            in 12..15 -> stringResource(R.string.greeting_afternoon)
            in 16..19 -> stringResource(R.string.greeting_evening)
            else -> stringResource(R.string.greeting_night)
        }
    }

    fun getFormattedStopwatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hrs = TimeUnit.MILLISECONDS.toHours(ms)
        milliseconds -= TimeUnit.HOURS.toMillis(hrs)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        val formattedString =
            "${if (hrs < 10) "0" else ""}$hrs:" +
                    "${if (minutes < 10) "0" else ""}$minutes:" +
                    "${if (seconds < 10) "0" else ""}$seconds"

        return if (!includeMillis) {
            formattedString
        } else {
            milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
            milliseconds /= 10
            formattedString + ":" +
                    "${if (milliseconds < 10) "0" else ""}$milliseconds"
        }
    }
}