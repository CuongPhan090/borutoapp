package com.cp.borutoapp.ui.sharedviewcomponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.ui.theme.SMALL_PADDING

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    onItemClick: ((Int) -> Unit)
) {
    val result = handlePagingResult(heroes)

    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(count = heroes.itemCount) { index ->
                heroes[index]?.let {
                    HeroItem(hero = it, onItemClick = onItemClick)
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
                ErrorScreen(error = error, heroes = heroes)
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
