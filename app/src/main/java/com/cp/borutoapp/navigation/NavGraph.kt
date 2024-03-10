package com.cp.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cp.borutoapp.presentation.screen.SplashScreen
import com.cp.borutoapp.presentation.screen.WelcomeScreen
import com.cp.borutoapp.util.Constant.DETAILS_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.Welcome.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navHostController)
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navHostController)
        }
        composable(route = Screen.Home.route) {

        }
        composable(route = Screen.Search.route) {

        }
        composable(
            route = Screen.Details.route, arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {

        }
    }
}
