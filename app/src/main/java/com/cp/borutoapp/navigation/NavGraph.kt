package com.cp.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cp.borutoapp.presentation.screen.detail.DetailScreen
import com.cp.borutoapp.presentation.screen.home.HomeScreen
import com.cp.borutoapp.presentation.screen.search.SearchScreen
import com.cp.borutoapp.presentation.screen.splash.SplashScreen
import com.cp.borutoapp.presentation.screen.welcome.WelcomeScreen
import com.cp.borutoapp.util.Constant.DETAILS_ARGUMENT_KEY

@Composable
fun SetUpNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Home.route)
                },
                onNavigateToWelcome = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.Welcome.route)
                }
            )
        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(onNavigateToHome = {
                navHostController.popBackStack()
                navHostController.navigate(Screen.Home.route)
            })
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToSearch = {
                    navHostController.navigate(Screen.Search.route)
                },
                onNavigateToDetail = { heroId ->
                    navHostController.navigate(Screen.Details.passHeroId(heroId))
                }
            )
        }
        composable(route = Screen.Search.route) {
            SearchScreen(
                onNavigateToDetail = { heroId ->
                    navHostController.navigate(Screen.Details.passHeroId(heroId))
                }
            )
        }
        composable(
            route = Screen.Details.route, arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(navController = navHostController)
        }
    }
}
