package com.cp.borutoapp.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.cp.borutoapp.ui.viewcomponent.ListContent

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val allHeroes = viewModel.getAllHeroes().collectAsLazyPagingItems()

    Scaffold(
        topBar = { HomeTopBar(onSearchClick = {}) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ListContent(heroes = allHeroes, navController = navController)
        }
    }
}
