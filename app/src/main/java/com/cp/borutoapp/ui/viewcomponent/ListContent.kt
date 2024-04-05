package com.cp.borutoapp.ui.viewcomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import kotlin.reflect.jvm.internal.impl.types.error.ErrorScope

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
    val result = handlePagingResult(heroes)

    if (result) {
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
}

@Composable
fun handlePagingResult(heroes: LazyPagingItems<Hero>): Boolean {
    with(heroes) {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as? LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as? LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as? LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                ErrorScreen(loadState = error)
                false
            }
            heroes.itemCount < 1 -> {
                ErrorScreen()
                false
            }
            else -> true
        }
    }
}
