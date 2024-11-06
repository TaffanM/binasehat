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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.ui.screen.components.BottomBar
import com.mage.binasehat.ui.screen.dashboard.DashboardScreen
import com.mage.binasehat.ui.screen.dashboard.NewsViewModel
import com.mage.binasehat.ui.screen.exercise.ExerciseScreen
import com.mage.binasehat.ui.screen.food.FoodScreen
import com.mage.binasehat.ui.screen.profile.ProfileScreen

@Composable
fun MainScreenWithBottomBar(
    navController: NavHostController,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val nestedNavController = rememberNavController()
    val currentRoute = nestedNavController.currentBackStackEntryAsState().value?.destination?.route ?: "home"
    val newsState by newsViewModel.newsState.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.fetchNews()
    }

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
            ) { DashboardScreen(navController, newsState) }
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
            ) { ExerciseScreen(navController)  }
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