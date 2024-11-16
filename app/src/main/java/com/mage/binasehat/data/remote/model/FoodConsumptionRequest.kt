package com.mage.binasehat.data.remote.model

data class FoodConsumptionRequest(
    val food_id: Int,
    val portion: Float,
    val consumed_at: String

)