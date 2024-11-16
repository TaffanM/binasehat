package com.mage.binasehat.data.remote.api

import com.mage.binasehat.data.remote.model.FoodConsumptionRequest
import com.mage.binasehat.data.remote.response.FoodConsumptionResponse
import com.mage.binasehat.data.remote.response.FoodHistoryResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodApiService {

    @POST("/food-consumption")
    suspend fun submitFoodConsumption(
        @Header("Authorization") authToken: String,
        @Body foodConsumptionRequest: FoodConsumptionRequest
    ): FoodConsumptionResponse

    @GET("food-history")
    suspend fun getFoodHistory(
        @Header("Authorization") authToken: String,  // Bearer token
        @Query("date") date: String? = null  // Optional date parameter (YYYY-MM-DD)
    ): Response<FoodHistoryResponse>

}