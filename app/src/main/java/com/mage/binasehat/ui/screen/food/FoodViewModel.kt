package com.mage.binasehat.ui.screen.food

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mage.binasehat.data.model.Food
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FoodViewModel : ViewModel() {
    // Using MutableStateFlow to manage the list of added items
    private val _addedItems = MutableStateFlow<List<Food>>(emptyList())
    val addedItems: StateFlow<List<Food>> get() = _addedItems

    // Adds an item to the list
    fun addItem(food: Food) {
        _addedItems.value += food
    }

    // Removes an item from the list
    fun removeItem(food: Food) {
        _addedItems.value -= food
    }
}