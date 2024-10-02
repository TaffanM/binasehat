package com.mage.binasehat.ui.screen.onboarding

import androidx.annotation.DrawableRes
import com.mage.binasehat.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val desc: Int
) {
    data object First : OnBoardingPage(
        image = R.drawable.dumbbell,
        desc = R.string.onboarding_desc1
    )

    data object Second : OnBoardingPage(
        image = R.drawable.food,
        desc = R.string.onboarding_desc2
    )

    data object Third : OnBoardingPage(
        image = R.drawable.workout,
        desc = R.string.onboarding_desc3
    )

}