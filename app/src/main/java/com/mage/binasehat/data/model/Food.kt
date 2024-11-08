package com.mage.binasehat.data.model

data class Food(
    val foodId: Int,
    val name: String,
    val calories: Int,
    val category: String,
    val sugar: Float,
    val protein: Float,
    val carb: Float,
    val fat: Float,
    val headline: String,
    val desc: String,
    val photo: Int
)

