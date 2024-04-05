package com.cp.borutoapp.presentation.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.Scaffold
import com.cp.borutoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    var query by viewModel.searchQuery

    var activeState by remember {
        mutableStateOf(false)
    }

    val heroes = viewModel.searchHeroResults.collectAsLazyPagingItems()

    Scaffold {
        SearchBar(modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = {
                viewModel.updateSearchQuery(it)
            },
            onSearch = {
                viewModel.searchHeroes(query = query)
            },
            active = activeState,
            onActiveChange = { activeState = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_here),
                    color = Color.Black.copy(alpha = ContentAlpha.medium),
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon),
                    tint = Color.Black.copy(alpha = ContentAlpha.medium)
                )
            },
            trailingIcon = {
                if (activeState) {
                    Icon(
                        modifier = Modifier.clickable {
                            query = ""
                            activeState = false
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = Color.Black
                    )
                }
            }
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}