package com.cp.borutoapp.presentation.screen.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.cp.borutoapp.R
import com.cp.borutoapp.ui.theme.Purple500
import com.cp.borutoapp.ui.theme.Purple700

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onNavigateToWelcome: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val rotationDegree = remember { Animatable(initialValue = 0f) }
    val shouldSkipOnBoarding by splashViewModel.shouldSkipOnBoarding.collectAsState()

    LaunchedEffect(key1 = true) {
        rotationDegree.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000, delayMillis = 300
            )
        )

//        navController.popBackStack()
        if (shouldSkipOnBoarding) {
            onNavigateToHome()
        } else {
            onNavigateToWelcome()
        }
    }

    Splash(rotationDegree.value)
}

@Composable
fun Splash(rotationDegree: Float) {
    val backgroundColorBrush =
        if (isSystemInDarkTheme()) Brush.verticalGradient(listOf(Color.Black, Color.Black))
        else Brush.verticalGradient(colors = listOf(Purple700, Purple500))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundColorBrush),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.rotate(degrees = rotationDegree),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.app_logo)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(rotationDegree = 0f)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenDarkThemePreview() {
    Splash(rotationDegree = 0f)
}
