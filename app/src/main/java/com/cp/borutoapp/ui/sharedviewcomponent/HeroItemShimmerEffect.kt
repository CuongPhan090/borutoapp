package com.cp.borutoapp.ui.sharedviewcomponent

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cp.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.cp.borutoapp.ui.theme.MEDIUM_PADDING
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import com.cp.borutoapp.ui.theme.ShimmerDarkGray
import com.cp.borutoapp.ui.theme.ShimmerLightGray
import com.cp.borutoapp.ui.theme.ShimmerMediumGray

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(count = 2) {
            AnimatedHeroItemShimmerEffect()
        }
    }
}

@Composable
fun AnimatedHeroItemShimmerEffect() {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    HeroItemShimmerEffect(alphaAnim = alphaAnim)
}

@Composable
fun HeroItemShimmerEffect(alphaAnim: Float) {
    Surface(
        modifier = Modifier
            .aspectRatio(ratio = 9f / 10f),
        shape = RoundedCornerShape(size = MEDIUM_PADDING),
        color = if (isSystemInDarkTheme()) Color.Black else ShimmerLightGray
    ) {
        Column(
            modifier = Modifier.padding(all = MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(40.dp)
                    .alpha(alpha = alphaAnim),
                shape = RoundedCornerShape(size = SMALL_PADDING),
                color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray
            ) {}

            Spacer(modifier = Modifier.padding(all = SMALL_PADDING))
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .padding(vertical = EXTRA_SMALL_PADDING)
                        .alpha(alpha = alphaAnim),
                    shape = RoundedCornerShape(size = SMALL_PADDING),
                    color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray
                ) {}
            }

            Spacer(modifier = Modifier.padding(all = SMALL_PADDING))
            Row {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(3.dp)
                            .alpha(alpha = alphaAnim),
                        shape = RoundedCornerShape(size = SMALL_PADDING),
                        color = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray
                    ) {}
                }
            }
        }
    }
}

@Preview
@Composable
fun HeroItemShimmerEffectPreview() {
    AnimatedHeroItemShimmerEffect()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemShimmerEffectPreviewInDarkMode() {
    AnimatedHeroItemShimmerEffect()
}