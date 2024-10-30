package com.mage.binasehat

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mage.binasehat.ui.screen.accountdetail.AccountDetailScreen
import com.mage.binasehat.ui.screen.food.CartScreen
import com.mage.binasehat.ui.screen.food.DetailFoodScreen
import com.mage.binasehat.ui.screen.food.FoodListScreen
import com.mage.binasehat.ui.screen.food.FoodViewModel
import com.mage.binasehat.ui.screen.form.FormScreen
import com.mage.binasehat.ui.screen.login.LoginScreen
import com.mage.binasehat.ui.screen.main.MainScreenWithBottomBar
import com.mage.binasehat.ui.screen.onboarding.OnboardingScreen
import com.mage.binasehat.ui.screen.register.RegisterScreen
import com.mage.binasehat.ui.screen.scan.ScanScreen
import com.mage.binasehat.ui.screen.settings.SettingsScreen

@Composable
fun BinaSehatApp(
    navController: NavHostController = rememberNavController()
) {
    val foodViewModel: FoodViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") { OnboardingScreen(navController)}
        composable("login") { LoginScreen(navController)}
        composable("register") { RegisterScreen(navController) }
        composable("form") { FormScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("account_detail") { AccountDetailScreen(navController) }
        composable("scan") { ScanScreen(navController) }
        composable("foodList",
            enterTransition = { return@composable fadeIn(tween(300)) },
            exitTransition = { return@composable fadeOut(tween(300)) }
        )
        { FoodListScreen(navController, foodViewModel) }
        composable(
            route = "detailFoodScreen/{foodId}",
            arguments = listOf(navArgument("foodId") { type = NavType.IntType }),
            enterTransition = {  return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300)) },
            exitTransition = { return@composable fadeOut(tween(300)) }
        ) {backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
            DetailFoodScreen(navController, foodId, foodViewModel)
        }
        composable(
            route = "cart",
            enterTransition = { return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) },
            exitTransition = { return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) }
        ) {
            CartScreen(navController)
        }
        composable("main") {
            MainScreenWithBottomBar(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BinaSehatAppPreview() {
    BinaSehatApp()
}