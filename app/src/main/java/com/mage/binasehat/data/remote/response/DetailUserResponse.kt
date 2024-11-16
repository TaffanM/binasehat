package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val userDetail: UserDetail
)

data class UserDetail(

	@field:SerializedName("photoUrl")
	val photoUrl: Any?,

	@field:SerializedName("dailyCaloriesOut")
	val dailyCaloriesOut: Int,

	@field:SerializedName("form")
	val form: Form,

	@field:SerializedName("lastCaloriesUpdateDate")
	val lastCaloriesUpdateDate: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("dailyCaloriesIn")
	val dailyCaloriesIn: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
