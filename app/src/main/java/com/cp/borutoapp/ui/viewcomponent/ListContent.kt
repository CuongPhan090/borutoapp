package com.cp.borutoapp.ui.viewcomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.ui.theme.SMALL_PADDING

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(count = heroes.itemCount) { index ->
            heroes[index]?.let {
                HeroItem(hero = it, navController = navController)
            }
        }
    }
}