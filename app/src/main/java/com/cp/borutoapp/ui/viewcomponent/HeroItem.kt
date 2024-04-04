package com.cp.borutoapp.ui.viewcomponent

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.cp.borutoapp.R
import com.cp.borutoapp.navigation.Screen
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.ui.theme.MEDIUM_PADDING
import com.cp.borutoapp.ui.theme.SMALL_PADDING
import com.cp.borutoapp.ui.theme.topAppBarContentColor
import com.cp.borutoapp.util.Constant.BASE_URL

@Composable
fun HeroItem(hero: Hero, navController: NavHostController) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data("$BASE_URL${hero.image}")
            .error(R.drawable.ic_placeholder)
            .placeholder(R.drawable.ic_placeholder)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 9f / 10f)
            .clip(RoundedCornerShape(MEDIUM_PADDING))
            .clickable {
                navController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "${hero.name} image",
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = Color.Black.copy(alpha = ContentAlpha.medium)
        ) {
            Column(modifier = Modifier.padding(MEDIUM_PADDING)) {
                Text(
                    text = hero.name,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.topAppBarContentColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Text(
                    text = hero.about,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    maxLines = 3
                )
                Row(modifier = Modifier.padding(top = SMALL_PADDING)) {
                    RatingWidget(rating = hero.rating)
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = SMALL_PADDING),
                        text = "(${hero.rating})",
                        color = MaterialTheme.colorScheme.topAppBarContentColor,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroItemPreview() {
    val navController = rememberNavController()
    val hero = Hero(
        id = 1,
        name = "Sasuke",
        image = "/images/sasuke.jpg",
        about = "Sasuke Uchiha (うちはサスケ, Uchiha Sasuke) is one of the last surviving members of Konohagakure's Uchiha clan. After his older brother, Itachi, slaughtered their clan, Sasuke made it his mission in life to avenge them by killing Itachi. He is added to Team 7 upon becoming a ninja and, through competition with his rival and best friend, Naruto Uzumaki.",
        rating = 4.7,
        power = 98,
        month = "July",
        day = "23rd",
        family = listOf(
            "Fugaku", "Mikoto", "Itachi", "Sarada", "Sakura"
        ),
        abilities = listOf(
            "Sharingan", "Rinnegan", "Sussano", "Amateratsu", "Intelligence"
        ),
        natureTypes = listOf(
            "Lightning", "Fire", "Wind", "Earth", "Water"
        )
    )
    HeroItem(hero, navController)
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, backgroundColor = 0x000000)
@Composable
fun HeroItemPreviewInDarkMode() {
    val navController = rememberNavController()
    val hero = Hero(
        id = 1,
        name = "Sasuke",
        image = "/images/sasuke.jpg",
        about = "Sasuke Uchiha (うちはサスケ, Uchiha Sasuke) is one of the last surviving members of Konohagakure's Uchiha clan. After his older brother, Itachi, slaughtered their clan, Sasuke made it his mission in life to avenge them by killing Itachi. He is added to Team 7 upon becoming a ninja and, through competition with his rival and best friend, Naruto Uzumaki.",
        rating = 3.2,
        power = 98,
        month = "July",
        day = "23rd",
        family = listOf(
            "Fugaku", "Mikoto", "Itachi", "Sarada", "Sakura"
        ),
        abilities = listOf(
            "Sharingan", "Rinnegan", "Sussano", "Amateratsu", "Intelligence"
        ),
        natureTypes = listOf(
            "Lightning", "Fire", "Wind", "Earth", "Water"
        )
    )
    HeroItem(hero, navController)
}