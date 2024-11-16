package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodConsumptionResponse(

	@field:SerializedName("consumed_at")
	val consumedAt: String,

	@field:SerializedName("portion")
	val portion: String,

	@field:SerializedName("food_id")
	val foodId: String
)
