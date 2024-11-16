package com.mage.binasehat.data.model

data class TodayExerciseSummary(
    val dateString: String,
    val muscleTarget: String,

    val total_exercise_today: Int,
    val total_exercise_finished: Int,
    val total_calori: Int
)