package com.cp.borutoapp.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cp.borutoapp.R
import com.cp.borutoapp.presentation.model.OnBoardingPage
import com.cp.borutoapp.ui.theme.EXTRA_LARGE_PADDING
import com.cp.borutoapp.ui.theme.LARGE_PADDING
import com.cp.borutoapp.ui.theme.MEDIUM_PADDING
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import com.cp.borutoapp.ui.theme.completeOnBoardingButton
import com.cp.borutoapp.ui.theme.descriptionColor
import com.cp.borutoapp.ui.theme.horizontalPagerIndicatorActive
import com.cp.borutoapp.ui.theme.horizontalPagerIndicatorInactive
import com.cp.borutoapp.ui.theme.titleColor
import com.cp.borutoapp.ui.theme.welcomeScreenBackgroundColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val onboardingPages = listOf(OnBoardingPage.First, OnBoardingPage.Second, OnBoardingPage.Third)

    val pagerState = rememberPagerState {
        onboardingPages.size
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.welcomeScreenBackgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(state = pagerState, modifier = Modifier.weight(0.7f)) { page ->
            PagerScreen(onBoardingPage = onboardingPages[page])
        }
        HorizontalPagerIndicator(pagerState = pagerState)
        CompleteOnBoardingButton(pagerState = pagerState) {

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompleteOnBoardingButton(pagerState: PagerState, onClickButton: () -> Unit) {
    AnimatedVisibility(visible = pagerState.currentPage == pagerState.pageCount - 1) {
        Button(
            onClick = onClickButton,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.completeOnBoardingButton),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING, vertical = LARGE_PADDING)
        ) {
            Text(
                text = stringResource(R.string.finish),
                color = Color.White,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = LARGE_PADDING),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.horizontalPagerIndicatorActive else MaterialTheme.colorScheme.horizontalPagerIndicatorInactive
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = stringResource(R.string.on_boarding_image),
            modifier = Modifier.fillMaxHeight(0.7f)
        )
        Text(
            text = onBoardingPage.title,
            color = MaterialTheme.colorScheme.titleColor,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = onBoardingPage.description,
            color = MaterialTheme.colorScheme.descriptionColor,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING, vertical = SMALL_PADDING)
        )
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController = navController)
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WelcomeScreenPreviewInDarkModePreview() {
    val navController = rememberNavController()
    WelcomeScreen(navController = navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun HorizontalPagerIndicationPreview() {
    val pagerState = rememberPagerState { 3 }
    HorizontalPagerIndicator(pagerState = pagerState)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x000000)
@Composable
fun HorizontalPagerIndicationInDarkModePreview() {
    val pagerState = rememberPagerState { 3 }
    HorizontalPagerIndicator(pagerState = pagerState)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CompleteOnBoardingButtonPreview() {
    val pagerState = rememberPagerState { 3 }
    CompleteOnBoardingButton(pagerState) {}
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CompleteOnBoardingButtonInDarkModePreview() {
    val pagerState = rememberPagerState { 3 }
    CompleteOnBoardingButton(pagerState) {}
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun FirstPagerScreenPreview() {
    PagerScreen(onBoardingPage = OnBoardingPage.First)
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x000000)
@Composable
fun FirstPagerScreenInDarkModePreview() {
    PagerScreen(onBoardingPage = OnBoardingPage.First)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SecondPagerScreenPreview() {
    PagerScreen(onBoardingPage = OnBoardingPage.Second)
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x000000)
@Composable
fun SecondPagerScreenInDarkModePreview() {
    PagerScreen(onBoardingPage = OnBoardingPage.Second)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ThirdPagerScreenPreview() {
    PagerScreen(onBoardingPage = OnBoardingPage.Third)
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x000000)
@Composable
fun ThirdPagerScreenInDarkModePreview() {
    PagerScreen(onBoardingPage = OnBoardingPage.Third)
}