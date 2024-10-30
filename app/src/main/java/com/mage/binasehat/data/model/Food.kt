package com.mage.binasehat.data.model

data class Food(
    val foodId: Int,
    val name: String,
    val calories: Int,
    val category: String,
    val sugar: Int,
    val protein: Int,
    val carb: Int,
    val fat: Int,
    val desc: String,
    val photo: Int
)
