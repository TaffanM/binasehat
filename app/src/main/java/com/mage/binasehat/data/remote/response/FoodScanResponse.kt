package com.mage.binasehat.data.remote.response

import com.google.gson.annotations.SerializedName

data class FoodScanResponse(

    @field:SerializedName("predicted_class")
    val predictedClass: String,

    @field:SerializedName("confidence")
    val confidence: Double
)
