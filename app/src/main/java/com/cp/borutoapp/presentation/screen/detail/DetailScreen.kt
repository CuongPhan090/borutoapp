package com.cp.borutoapp.presentation.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cp.borutoapp.util.Constant.BASE_URL
import com.cp.borutoapp.util.PaletteGenerator.convertImageUrlToBitMap
import com.cp.borutoapp.util.PaletteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    navController: NavHostController,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {

    val selectedHero by viewModel.selectedHero.collectAsState()
    val colorPalette by viewModel.colorPalette


    if (colorPalette.isNotEmpty()) {
        DetailContent(selectedHero = selectedHero, navController = navController, colors = colorPalette)
    } else {
        viewModel.generateColorPalette()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitMap(
                        imageUrl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )

                    if (bitmap != null) {
                        viewModel.setColorPalette(
                            colors = extractColorsFromBitmap(bitmap)
                        )
                    }
                }
            }
        }
    }
}
