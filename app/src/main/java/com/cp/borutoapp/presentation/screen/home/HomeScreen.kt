package com.cp.borutoapp.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.cp.borutoapp.ui.theme.LARGE_PADDING
import com.cp.borutoapp.ui.viewcomponent.RatingWidget

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
            RatingWidget(modifier = Modifier.padding(LARGE_PADDING), rating = 3.2)
        }
    }
}

