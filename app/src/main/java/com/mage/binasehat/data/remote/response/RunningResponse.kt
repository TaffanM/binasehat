package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class RunningResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class RunningLog(

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("distance")
	val distance: String,

	@field:SerializedName("calories_burned")
	val caloriesBurned: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("run_date")
	val runDate: String
)

data class Data(

	@field:SerializedName("runningLog")
	val runningLog: RunningLog,

	@field:SerializedName("dailyCaloriesOut")
	val dailyCaloriesOut: String
)
