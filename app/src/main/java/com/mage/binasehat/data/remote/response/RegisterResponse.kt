package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("token")
	val token: String
)

data class User(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

