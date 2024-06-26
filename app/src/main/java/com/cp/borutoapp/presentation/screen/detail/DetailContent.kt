package com.cp.borutoapp.presentation.screen.detail

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.cp.borutoapp.R
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.ui.sharedviewcomponent.InfoBox
import com.cp.borutoapp.ui.sharedviewcomponent.OrderedList
import com.cp.borutoapp.ui.theme.BOTTOM_SHEET_PEEK_HEIGHT
import com.cp.borutoapp.ui.theme.CLOSE_ICON_SIZE
import com.cp.borutoapp.ui.theme.INFO_BOX_ICON_SIZE
import com.cp.borutoapp.ui.theme.LARGE_PADDING
import com.cp.borutoapp.ui.theme.MEDIUM_PADDING
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import com.cp.borutoapp.ui.theme.titleColor
import com.cp.borutoapp.util.Constant

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailContent(
    selectedHero: Hero?,
    onCloseClick: () -> Unit,
    colors: Map<String, String>
) {
    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#000000") }

    LaunchedEffect(key1 = selectedHero) {
        vibrant = colors["vibrant"] ?: "#000000"
        darkVibrant = colors["darkVibrant"] ?: "#000000"
        onDarkVibrant = colors["onDarkVibrant"] ?: "#000000"
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )

    val currentBottomSheetFraction = bottomSheetScaffoldState.currentBottomSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue = if (currentBottomSheetFraction == 1f) LARGE_PADDING else 0.dp,
        label = ""
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(size = radiusAnim),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = BOTTOM_SHEET_PEEK_HEIGHT,
        sheetContent = {
            selectedHero?.let { hero ->
                BottomSheetContent(
                    hero = hero,
                    infoBoxIconColor = Color(parseColor(vibrant)),
                    sheetBackgroundColor = Color(parseColor(darkVibrant)),
                    contentColor = Color(parseColor(onDarkVibrant))
                )

            }
        }) {
        selectedHero?.let { hero ->
            DetailContent(
                imageUrl = hero.image,
                imageFraction = currentBottomSheetFraction,
                backgroundColor = Color(parseColor(darkVibrant))
            ) {
                onCloseClick()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentBottomSheetFraction: Float
    // Ref: https://www.droidcon.com/wp-content/uploads/2021/09/0_BkazrCBWnpVH2-Ig.gif
    get() {
        val fraction = bottomSheetState.progress
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue
        return when {
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
            currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
            currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
            else -> fraction
        }
    }

@Composable
fun BottomSheetContent(
    hero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = titleColor
) {
    Column(
        modifier = Modifier
            .background(color = sheetBackgroundColor)
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .padding(vertical = MEDIUM_PADDING, horizontal = SMALL_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(INFO_BOX_ICON_SIZE),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = stringResource(R.string.logo_icon),
                tint = contentColor
            )

            Text(
                modifier = Modifier.padding(start = SMALL_PADDING),
                text = hero.name,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_bolt),
                iconColor = infoBoxIconColor,
                bigText = "${hero.power}",
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )

            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar),
                iconColor = infoBoxIconColor,
                bigText = hero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )

            InfoBox(
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = hero.day,
                smallText = stringResource(R.string.birthday),
                textColor = contentColor
            )
        }

        Text(
            modifier = Modifier
                .padding(bottom = SMALL_PADDING),
            text = stringResource(R.string.about),
            fontSize = MaterialTheme.typography.body1.fontSize,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )

        Text(
            text = hero.about,
            modifier = Modifier
                .padding(bottom = SMALL_PADDING)
                .alpha(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = contentColor,
            maxLines = 6,
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OrderedList(
                title = stringResource(R.string.family),
                items = hero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = hero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = hero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@Composable
fun DetailContent(
    imageUrl: String,
    imageFraction: Float,
    backgroundColor: Color,
    onCloseClicked: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("${Constant.BASE_URL}$imageUrl")
            .error(R.drawable.ic_placeholder)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Image(
            modifier = Modifier.fillMaxHeight(fraction = imageFraction + 0.35f).fillMaxWidth(),
            painter = painter,
            contentDescription = stringResource(R.string.hero_image),
            contentScale = ContentScale.Crop
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = onCloseClicked) {
                Icon(
                    modifier = Modifier.size(CLOSE_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentInPreview() {
    BottomSheetContent(
        hero = Hero(
            id = 10,
            name = "Isshiki",
            image = "/images/ishiki.jpg",
            about = "A thousand years ago, Isshiki came to Earth alongside Kaguya with the objective to plant a Tree to harvest its Chakra Fruit. While Kaguya, being lower-ranked, was planned to be sacrificed to create the Chakra Fruit, she instead turned on Isshiki, leaving him on the verge of death after destroying Isshiki's lower half. Encountering Jigen and not having the strength to implant a Kāma on him, Isshiki devised a desperate plan and shrunk himself to enter the monk's ear in order to survive his injury by absorbing Jigen's nutrients.",
            rating = 5.0,
            power = 100,
            month = "Jan",
            day = "1st",
            family = listOf(
                "Otsutsuki Clan"
            ),
            abilities = listOf(
                "Sukunahikona",
                "Daikokuten",
                "Byakugan",
                "Space–Time",
                "Intelligence"
            ),
            natureTypes = listOf(
                "Fire"
            )
        )
    )
}