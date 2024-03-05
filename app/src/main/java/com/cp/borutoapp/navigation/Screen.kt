package com.cp.borutoapp.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Welcome : Screen("welcome_screen")
    data object Search : Screen("search_screen")
    data object Home : Screen("home_screen")
    data object Details : Screen("details_screen/{heroId}") {
        fun passHeroId(heroId: Int) = "details_screen/$heroId"
    }
}
