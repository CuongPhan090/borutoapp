package com.cp.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cp.borutoapp.presentation.screen.home.HomeScreen
import com.cp.borutoapp.presentation.screen.search.SearchScreen
import com.cp.borutoapp.presentation.screen.splash.SplashScreen
import com.cp.borutoapp.presentation.screen.welcome.WelcomeScreen
import com.cp.borutoapp.util.Constant.DETAILS_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navHostController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screen.Search.route) {
            SearchScreen()
        }
        composable(
            route = Screen.Details.route, arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {

        }
    }
}
