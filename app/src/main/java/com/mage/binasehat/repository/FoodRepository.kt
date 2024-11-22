package com.mage.binasehat.repository

import android.util.Log
import com.mage.binasehat.data.remote.api.FoodApiService
import com.mage.binasehat.data.remote.model.FoodConsumptionRequest
import com.mage.binasehat.data.remote.response.FoodConsumptionResponse
import com.mage.binasehat.data.remote.response.FoodHistoryResponse
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodApiService: FoodApiService
) {

    suspend fun submitFoodConsumption(
        authToken: String,
        foodConsumptionRequests: List<FoodConsumptionRequest>
    ): List<FoodConsumptionResponse> {
        val responses = mutableListOf<FoodConsumptionResponse>()

        try {
            for (request in foodConsumptionRequests) {
                val response = foodApiService.submitFoodConsumption(authToken, request)
                responses.add(response)
            }
        } catch (e: Exception) {
            // Handle exception if necessary (e.g., logging or rethrowing)
            throw Exception("Error submitting food consumption: ${e.message}")
        }

        return responses
    }

    suspend fun getFoodHistory(authToken: String, date: String? = null): FoodHistoryResponse? {
        try {
            // Make the API request to fetch the food history
            val response = foodApiService.getFoodHistory("Bearer $authToken", date)
            Log.d("FoodRepository", "API Response: $response")
            Log.d("FoodRepository", "Date: ${date}")

            if (response.isSuccessful) {
                return response.body()  // Return the response body if successful
            } else {
                Log.e("FoodRepository", "Error fetching food history: ${response.code()}")
                return null  // Return null in case of an error
            }
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error in fetching food history", e)
            return null  // Return null if there is an exception
        }
    }
}