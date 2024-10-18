package com.mage.binasehat.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mage.binasehat.R
import java.time.LocalTime

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
}