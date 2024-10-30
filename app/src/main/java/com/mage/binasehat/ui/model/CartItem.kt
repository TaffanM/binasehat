package com.mage.binasehat.ui.model

import com.mage.binasehat.data.model.Food

data class CartItem(
    val food: Food,
    var quantity: Int
)
