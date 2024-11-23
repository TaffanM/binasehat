package com.mage.binasehat

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mage.binasehat.repository.UserRepository
import com.mage.binasehat.ui.screen.accountdetail.AccountDetailScreen
import com.mage.binasehat.ui.screen.accountdetail.AccountDetailViewModel
import com.mage.binasehat.ui.screen.dashboard.DashboardViewModel
import com.mage.binasehat.ui.screen.food.CartScreen
import com.mage.binasehat.ui.screen.food.DetailFoodScreen
import com.mage.binasehat.ui.screen.food.FoodListScreen
import com.mage.binasehat.ui.screen.food.FoodViewModel
import com.mage.binasehat.ui.screen.form.FormScreen
import com.mage.binasehat.ui.screen.form.UserRegistrationViewModel
import com.mage.binasehat.ui.screen.login.LoginScreen
import com.mage.binasehat.ui.screen.login.LoginViewModel
import com.mage.binasehat.ui.screen.main.MainScreenWithBottomBar
import com.mage.binasehat.ui.screen.onboarding.OnboardingScreen
import com.mage.binasehat.ui.screen.register.RegisterScreen
import com.mage.binasehat.ui.screen.running.current.CurrentRunScreen
import com.mage.binasehat.ui.screen.running.current.CurrentRunViewModel
import com.mage.binasehat.ui.screen.running.RunViewModel
import com.mage.binasehat.ui.screen.running.history.RunningHistoryScreen
import com.mage.binasehat.ui.screen.running.history.RunningHistoryViewModel
import com.mage.binasehat.ui.screen.running.main.RunningMainScreen
import com.mage.binasehat.ui.screen.scan.FoodPredictViewModel
import com.mage.binasehat.ui.screen.scan.ScanScreen
import com.mage.binasehat.ui.screen.settings.SettingsScreen
import com.mage.binasehat.ui.screen.workout.detail.DetailWorkoutScreen
import com.mage.binasehat.ui.screen.workout.detail.DetailWorkoutViewModel
import com.mage.binasehat.ui.screen.workout.interactive.InteractiveLearnScreen
import com.mage.binasehat.ui.screen.workout.interactive.InteractiveLearnViewModel
import com.mage.binasehat.ui.screen.workout.main.WorkoutMainScreen
import com.mage.binasehat.ui.screen.workout.main.WorkoutMainViewModel

@Composable
fun BinaSehatApp(
    navController: NavHostController = rememberNavController(),
    userRepository: UserRepository
) {
    val foodViewModel: FoodViewModel = viewModel()
    val runningViewModel: RunViewModel = hiltViewModel()
    val currentRunningViewModel: CurrentRunViewModel = hiltViewModel()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val runningHistoryViewModel: RunningHistoryViewModel = hiltViewModel()
    val workoutMainViewModel: WorkoutMainViewModel = hiltViewModel()
    val detailWorkoutViewModel: DetailWorkoutViewModel = hiltViewModel()
    val userRegistrationViewModel: UserRegistrationViewModel = hiltViewModel()
    val foodPredictViewModel: FoodPredictViewModel = hiltViewModel()
    val accountDetailViewModel: AccountDetailViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val interactiveLearnViewModel: InteractiveLearnViewModel = hiltViewModel()

    // This holds the state of the app
    val runningState by runningViewModel.runningMainScreenState.collectAsState()


    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreen(navController) // You can navigate from here once onboarding is complete
        }

        // After Onboarding, the app navigates to this screen to check the token
        composable("launch") {
            LaunchedEffect(Unit) {
                val token = userRepository.getUserToken()
                if (token.isNullOrBlank()) {
                    navController.navigate("login")
                } else {
                    navController.navigate("main") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }
        }

        composable("login") {
            LoginScreen(navController, loginViewModel)
        }
        composable("register") {
            RegisterScreen(navController, userRegistrationViewModel)
        }
        composable("form") {
            FormScreen(navController, userRegistrationViewModel)
        }
        composable(
            route = "settings",
            enterTransition = { return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500)) },
            exitTransition = { return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500)) }
        ) {
            SettingsScreen(navController, loginViewModel)
        }
        composable("account_detail") {
            AccountDetailScreen(navController, accountDetailViewModel)
        }
        composable("scan") {
            ScanScreen(navController, foodPredictViewModel)
        }
        composable("foodList",
            enterTransition = { return@composable fadeIn(tween(300)) },
            exitTransition = { return@composable fadeOut(tween(300)) }
        ) {
            FoodListScreen(navController, foodViewModel)
        }
        composable(
            route = "detailFoodScreen/{foodId}",
            arguments = listOf(navArgument("foodId") { type = NavType.IntType }),
            enterTransition = { return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300)) },
            exitTransition = { return@composable fadeOut(tween(300)) }
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: 0
            DetailFoodScreen(navController, foodId, foodViewModel)
        }
        composable(
            route = "cart",
            enterTransition = { return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) },
            exitTransition = { return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) }
        ) {
            CartScreen(navController, foodViewModel)
        }
        composable(
            route = "main",
            enterTransition = { return@composable fadeIn(tween(100)) },
            exitTransition = { return@composable fadeOut(tween(100)) }
        ) {
            MainScreenWithBottomBar(navController = navController, newsViewModel = hiltViewModel(), dashboardViewModel, foodViewModel)
        }
        composable(
            route = "runningMainScreen",
            enterTransition = { return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300)) },
            exitTransition = { return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(300)) }
            ) {
            RunningMainScreen(navController = navController, state = runningState, runViewModel = runningViewModel)
        }
        composable(
            route = "currentRunningScreen",
            enterTransition = { return@composable slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, tween(300)) },
            exitTransition = { return@composable slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down, tween(300)) }
        ) {
            CurrentRunScreen(navController = navController, viewModel = currentRunningViewModel)
        }
        composable(
            route = "historyRunningScreen",
            enterTransition = { return@composable fadeIn(tween(100)) },
            exitTransition = { return@composable fadeOut(tween(100)) }
        ) {
            RunningHistoryScreen(navController = navController, viewModel = runningHistoryViewModel)
        }
        composable(
            route = "workoutMainScreen",
            enterTransition = { return@composable fadeIn(tween(100)) },
            exitTransition = { return@composable fadeOut(tween(100)) }
        ) {
            WorkoutMainScreen(
                navController = navController,
                navigateToDetail = { navController.navigate("detailWorkoutScreen/$it") },
                navigateToDetailSchedule = { navController.navigate("scheduleWorkoutScreen/$it") },
                viewModel = workoutMainViewModel
            )
        }
        composable(
            route = "detailWorkoutScreen/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.IntType }),
            enterTransition = { return@composable fadeIn(tween(100)) },
            exitTransition = { return@composable fadeOut(tween(100)) }
        ) { it ->
            val workoutId = it.arguments?.getInt("workoutId") ?: 0
            DetailWorkoutScreen(
                workoutId = workoutId,
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToInteractiveArea = {
                    navController.navigate("interactiveWorkoutScreen/$it")
                },
                detailWorkoutViewModel = detailWorkoutViewModel


            )
        }
        composable(
            route ="interactiveWorkoutScreen/{workoutId}",
            arguments = listOf(navArgument("workoutId") { type = NavType.IntType }),
            enterTransition = { return@composable fadeIn(tween(100)) },
            exitTransition = { return@composable fadeOut(tween(100)) }
        ) {
            val workoutId = it.arguments?.getInt("workoutId") ?: 0
            InteractiveLearnScreen(
                workoutId = workoutId,
                navigateToHome = {
                    navController.popBackStack()
                },
                viewModel = interactiveLearnViewModel


            )

        }



    }
}
