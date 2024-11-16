package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String
)