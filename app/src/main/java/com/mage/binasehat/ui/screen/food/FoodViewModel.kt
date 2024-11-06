package com.mage.binasehat.ui.screen.food

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mage.binasehat.data.model.Food
import com.mage.binasehat.ui.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FoodViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    fun addItem(food: Food, quantity: Int) {
        val existingItem = _cartItems.value.find { it.food.foodId == food.foodId }
        val updatedCartItems = if (existingItem != null) {
            // If the item exists, increase its quantity
            _cartItems.value.map {
                if (it.food.foodId == food.foodId) it.copy(quantity = it.quantity + quantity) else it
            }
        } else {
            // If the item doesn't exist, add it to the list
            _cartItems.value + CartItem(food, quantity)
        }
        // Update the StateFlow with the new list
        _cartItems.value = updatedCartItems
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


}