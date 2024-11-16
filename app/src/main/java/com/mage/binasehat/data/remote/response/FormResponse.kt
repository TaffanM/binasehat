package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class FormResponse(

	@field:SerializedName("form")
	val form: Form,

	@field:SerializedName("dailyCalories")
	val dailyCalories: String,

	@field:SerializedName("macronutrients")
	val macronutrients: Macronutrients,

	@field:SerializedName("message")
	val message: String
)

data class Form(

	@field:SerializedName("weigh")
	val weigh: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("birt")
	val birt: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tall")
	val tall: String
)

data class Macronutrients(

	@field:SerializedName("carbs")
	val carbs: String,

	@field:SerializedName("fats")
	val fats: String,

	@field:SerializedName("protein")
	val protein: String,

	@field:SerializedName("sugar")
	val sugar: String
)
