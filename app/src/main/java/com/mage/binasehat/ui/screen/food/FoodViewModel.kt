package com.mage.binasehat.ui.screen.food

import android.text.format.DateUtils
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.model.Food
import com.mage.binasehat.data.remote.api.FoodApiService
import com.mage.binasehat.data.remote.model.FoodConsumptionRequest
import com.mage.binasehat.data.remote.response.FoodHistoryResponse
import com.mage.binasehat.repository.FoodRepository
import com.mage.binasehat.repository.UserRepository
import com.mage.binasehat.ui.model.CartItem
import com.mage.binasehat.ui.util.TimeUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _foodHistory = MutableStateFlow<FoodHistoryResponse?>(null)
    val foodHistory: StateFlow<FoodHistoryResponse?> = _foodHistory

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun addItem(food: Food, quantity: Int) {
        viewModelScope.launch {
            val currentItems = _cartItems.value
            val existingItem = currentItems.find { it.food.foodId == food.foodId }

            val updatedItems = if (existingItem != null) {
                currentItems.map { item ->
                    if (item.food.foodId == food.foodId) {
                        item.copy(quantity = item.quantity + quantity)
                    } else {
                        item
                    }
                }
            } else {
                currentItems + CartItem(food, quantity)
            }

            Log.d("FoodViewModel", "Adding item: ${food.name}, Current cart size: ${currentItems.size}, New size: ${updatedItems.size}")
            _cartItems.value = updatedItems
        }
    }

    fun removeItem(food: Food) {
        val existingItem = _cartItems.value.find { it.food.foodId == food.foodId }
        if (existingItem != null) {
            val updatedCartItems = _cartItems.value.filter { it.food.foodId != food.foodId }
            _cartItems.value = updatedCartItems
        }
    }

    fun increaseQuantity(food: Food) {
        _cartItems.update { cart ->
            cart.map { item ->
                if (item.food.foodId == food.foodId) {
                    item.copy(quantity = item.quantity  + 1)
                } else item
            }
        }
    }

    fun decreaseQuantity(food: Food) {
        _cartItems.update { cart ->
            cart.map { item ->
                if (item.food.foodId == food.foodId && item.quantity > 1) {
                    item.copy(quantity = item.quantity - 1)
                } else item
            }
        }
    }

    private fun resetCart() {
        _cartItems.value = emptyList()  // Reset the cart items to an empty list
        Log.d("FoodViewModel", "Cart reset after food consumption submission.")
    }


    fun submitFoodConsumption() {
        viewModelScope.launch {
            try {
                // Retrieve the authToken from UserRepository
                val authToken = userRepository.getUserToken()

                if (authToken != null) {
                    val requests = _cartItems.value.map { cartItem ->
                        val consumedAt = LocalDateTime.now()  // Uses system default timezone
                            .atZone(ZoneId.systemDefault())  // Explicitly set to system default zone
                            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        Log.d("FoodViewModel", "date: $consumedAt")
                        FoodConsumptionRequest(
                            food_id = cartItem.food.foodId,
                            portion = cartItem.quantity.toFloat(),
                            consumed_at = consumedAt
                        )

                    }
                    Log.d("FoodViewModel", "Submitting food consumption requests: $requests")

                    // Submit the food consumption requests via the repository
                    foodRepository.submitFoodConsumption("Bearer $authToken", requests)

                    resetCart()

                } else {
                    // Handle case where token is null (e.g., show login prompt)
                    Log.e("FoodViewModel", "Auth token is null")
                }

            } catch (e: Exception) {
                Log.e("FoodViewModel", "Error submitting food consumption", e)
            }
        }
    }

    // Fetch food consumption history
    fun getFoodHistory(date: String? = null) {
        viewModelScope.launch {
            _loading.value = true  // Set loading to true when the request starts
            _errorMessage.value = null  // Clear any previous errors

            try {
                // Get the auth token from the UserRepository
                val authToken = userRepository.getUserToken()

                if (authToken != null) {
                    // Fetch the food history from the repository
                    val response = foodRepository.getFoodHistory(authToken, date)
                    Log.d("FoodViewModel", "Date: $date")
                    if (response != null) {
                        _foodHistory.value = response  // Set the response to foodHistory
                    } else {
                        _errorMessage.value = "Failed to fetch food history."
                    }
                } else {
                    _errorMessage.value = "Authentication token is missing."
                }
            } catch (e: Exception) {
                // Handle exceptions like network errors
                _errorMessage.value = "Error fetching food history: ${e.message}"
            } finally {
                _loading.value = false  // Set loading to false after the request completes
            }
        }
    }
}