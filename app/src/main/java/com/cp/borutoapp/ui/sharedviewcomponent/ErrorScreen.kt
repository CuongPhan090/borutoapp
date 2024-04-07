package com.cp.borutoapp.ui.sharedviewcomponent

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cp.borutoapp.R
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ErrorScreen(error: LoadState.Error? = null, heroes: LazyPagingItems<Hero>? = null) {
    var errorMessage by remember {
        mutableStateOf(parseErrorMessage(error))
    }

    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    if (error == null) {
        errorMessage = "Find Your Favorite Hero"
        icon = R.drawable.ic_search_document
    }

    // One time animation
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    ErrorScreenContent(
        alphaAnim = alphaAnim,
        icon = icon,
        errorMessage = errorMessage,
        heroes = heroes,
        error = error
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ErrorScreenContent(
    alphaAnim: Float,
    icon: Int,
    errorMessage: String,
    heroes: LazyPagingItems<Hero>? = null,
    error: LoadState.Error? = null
) {

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            heroes?.refresh()
            isRefreshing = false
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = refreshState, enabled = error != null),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullRefreshIndicator(refreshing = isRefreshing, state = refreshState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(150.dp)
                    .alpha(alpha = alphaAnim),
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.network_error_icon),
                tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            )

            Text(
                modifier = Modifier
                    .padding(vertical = SMALL_PADDING)
                    .alpha(alpha = alphaAnim),
                text = errorMessage,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Medium,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            )
        }
    }
}

fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> "Server Unavailable"
        is ConnectException -> "Internal Unavailable"
        else -> "Unknown Error"
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenContentPreview() {
    val icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }
    ErrorScreenContent(alphaAnim = 1f, icon = icon, errorMessage = "SocketTimeoutException")
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000)
@Composable
fun ErrorScreenContentInDarkModePreview() {
    val icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }
    ErrorScreenContent(alphaAnim = 1f, icon = icon, errorMessage = "SocketTimeoutException")
}
