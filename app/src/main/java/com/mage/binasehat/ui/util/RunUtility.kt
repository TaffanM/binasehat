package com.mage.binasehat.ui.util

object RunUtility {
    fun calculateCaloriesBurnt(distanceInMeters: Int, weightInKg: Float) =
        (0.75f * weightInKg) * (distanceInMeters / 1000f)
}