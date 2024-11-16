package com.mage.binasehat.data.remote.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val photoUrl: String? = null
)
