package com.mage.binasehat

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mage.binasehat.ui.screen.form.FormScreen
import com.mage.binasehat.ui.screen.login.LoginScreen
import com.mage.binasehat.ui.screen.main.MainScreenWithBottomBar
import com.mage.binasehat.ui.screen.onboarding.OnboardingScreen
import com.mage.binasehat.ui.screen.register.RegisterScreen

@Composable
fun BinaSehatApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") { OnboardingScreen(navController)}
        composable("login") { LoginScreen(navController)}
        composable("register") { RegisterScreen(navController) }
        composable("form") { FormScreen(navController) }
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