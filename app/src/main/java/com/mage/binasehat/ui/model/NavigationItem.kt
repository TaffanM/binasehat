package com.mage.binasehat.ui.model

import androidx.annotation.DrawableRes

data class NavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val route: String,
)