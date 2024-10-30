package com.mage.binasehat.ui.screen.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.ui.screen.components.BottomBar
import com.mage.binasehat.ui.screen.dashboard.DashboardScreen
import com.mage.binasehat.ui.screen.food.FoodScreen
import com.mage.binasehat.ui.screen.profile.ProfileScreen

@Composable
fun MainScreenWithBottomBar(
    navController: NavHostController
) {
    val nestedNavController = rememberNavController()
    val currentRoute = nestedNavController.currentBackStackEntryAsState().value?.destination?.route ?: "home"

    Scaffold(
        bottomBar = {
            BottomBar(navController = nestedNavController, currentRoute = currentRoute)
        }
    ) { paddingValues ->

        NavHost(
            navController = nestedNavController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home",
                enterTransition = {
                    return@composable fadeIn(
                        animationSpec = tween(200)
                    )
                },
                exitTransition = {
                    return@composable fadeOut(
                        animationSpec = tween(200)
                    )
                }
            ) { DashboardScreen(navController) }
            composable("food",
                enterTransition = {
                    return@composable fadeIn(
                        animationSpec = tween(200)
                    )
                },
                exitTransition = {
                    return@composable fadeOut(
                        animationSpec = tween(200)
                    )
                }
            ) { FoodScreen(navController) }
            composable("exercise",
                enterTransition = {
                    return@composable fadeIn(
                        animationSpec = tween(200)
                    )
                },
                exitTransition = {
                    return@composable fadeOut(
                        animationSpec = tween(200)
                    )
                }
            ) { /* ExerciseScreen() */ }
            composable("profile",
                enterTransition = {
                    return@composable fadeIn(
                        animationSpec = tween(200)
                    )
                },
                exitTransition = {
                    return@composable fadeOut(
                        animationSpec = tween(200)
                    )
                }
            ) { ProfileScreen(navController) }
        }
    }
}