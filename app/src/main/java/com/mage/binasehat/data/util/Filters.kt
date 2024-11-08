package com.mage.binasehat.data.util

import android.content.Context
import com.mage.binasehat.R

enum class RunSortOrder {
    DATE,
    DURATION,
    CALORIES_BURNED,
    AVG_SPEED,
    DISTANCE;

    fun getLocalizedString(context: Context): String {
        return when (this) {
            DATE -> context.getString(R.string.run_sort_order_date)
            DURATION -> context.getString(R.string.run_sort_order_duration)
            CALORIES_BURNED -> context.getString(R.string.run_sort_order_calories_burned)
            AVG_SPEED -> context.getString(R.string.run_sort_order_avg_speed)
            DISTANCE -> context.getString(R.string.run_sort_order_distance)
        }
    }

    override fun toString(): String {
        return super.toString()
            .lowercase()
            .replace('_', ' ')
            .replaceFirstChar { it.uppercase() }
    }
}