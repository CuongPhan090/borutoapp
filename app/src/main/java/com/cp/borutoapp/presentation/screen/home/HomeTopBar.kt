package com.cp.borutoapp.presentation.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.cp.borutoapp.R
import com.cp.borutoapp.ui.theme.topAppBarBackgroundColor
import com.cp.borutoapp.ui.theme.topAppBarContentColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onSearchClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Explore",
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor),
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = Color.White
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar {}
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeTopBarPreviewInDarkMode() {
    HomeTopBar {}
}