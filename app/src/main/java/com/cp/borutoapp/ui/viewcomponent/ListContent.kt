package com.cp.borutoapp.ui.viewcomponent

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.cp.borutoapp.presentation.model.Hero

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
}