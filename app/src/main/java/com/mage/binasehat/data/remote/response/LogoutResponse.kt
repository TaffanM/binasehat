package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("logout_time")
	val logoutTime: String,

	@field:SerializedName("message")
	val message: String
)
