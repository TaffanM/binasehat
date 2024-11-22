package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodHistoryResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("foodList")
	val foodList: List<FoodListItem>,

	@field:SerializedName("totalNutrition")
	val totalNutrition: TotalNutrition
)

data class FoodListItem(

	@field:SerializedName("carbs")
	val carbs: Int,

	@field:SerializedName("fats")
	val fats: Any,

	@field:SerializedName("consumed_at")
	val consumedAt: String,

	@field:SerializedName("portion")
	val portion: Int,

	@field:SerializedName("protein")
	val protein: Any,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("calories")
	val calories: Int,

	@field:SerializedName("sugar")
	val sugar: Any
)

data class TotalNutrition(

	@field:SerializedName("carbs")
	val carbs: Int,

	@field:SerializedName("fats")
	val fats: Double,

	@field:SerializedName("protein")
	val protein: Double,

	@field:SerializedName("calories")
	val calories: Int,

	@field:SerializedName("sugar")
	val sugar: Double
)
