package com.cp.borutoapp.ui.viewcomponent

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.cp.borutoapp.R
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun ErrorScreen(loadState: LoadState.Error) {
    val errorMessage by remember {
        mutableStateOf(parseErrorMessage(loadState))
    }

    val icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
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

    ErrorScreenContent(alphaAnim, icon, errorMessage)
}

@Composable
private fun ErrorScreenContent(alphaAnim: Float, icon: Int, errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
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

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
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
