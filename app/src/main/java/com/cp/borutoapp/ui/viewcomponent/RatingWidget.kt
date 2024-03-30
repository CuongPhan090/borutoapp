package com.cp.borutoapp.ui.viewcomponent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cp.borutoapp.R
import com.cp.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.cp.borutoapp.ui.theme.LightGray
import com.cp.borutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    rating: Double,
    modifier: Modifier = Modifier,
    scaleFactor: Float = 2f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {
    val numberOfStars = calculateStars1(rating = rating)
    val startPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spaceBetween)) {
        repeat(numberOfStars[RatingWidgetState.FilledStars.name] ?: 0) {
            FilledStar(
                starPath = starPath,
                starPathBounds = starPathBounds,
                scaleFactor = scaleFactor
            )
        }

        repeat(numberOfStars[RatingWidgetState.HalfFilledStar.name] ?: 0) {
            HalfFilledStar(
                starPath = starPath,
                starPathBounds = starPathBounds,
                scaleFactor = scaleFactor
            )
        }

        repeat(numberOfStars[RatingWidgetState.EmptyStars.name] ?: 0) {
            EmptyStar(
                starPath = starPath,
                starPathBounds = starPathBounds,
                scaleFactor = scaleFactor
            )
        }
    }
}

@Composable
fun calculateStars1(rating: Double): Map<String, Int> {
    val starMap = remember { mutableStateMapOf<String, Int>() }
    val maxStars = remember { mutableIntStateOf(5) }

    LaunchedEffect(key1 = rating) {
        val (filledStar, halfStar) = rating.toString()
            .split(".")
            .map { it.toInt() }

        if (filledStar in 0..5 && halfStar in 0..9) {
            when (filledStar) {
                5 -> starMap[RatingWidgetState.FilledStars.name] = 5
                0 -> starMap[RatingWidgetState.EmptyStars.name] = 5
                in 1..4 -> {
                    starMap[RatingWidgetState.FilledStars.name] = filledStar
                    if (halfStar in 1..9) {
                        starMap[RatingWidgetState.HalfFilledStar.name] = 1
                    }
                    starMap[RatingWidgetState.EmptyStars.name] = maxStars.intValue - filledStar - 1
                }
            }
        }
    }
    return starMap
}

sealed class RatingWidgetState(val name: String) {
    data object FilledStars : RatingWidgetState("filled_stars")
    data object HalfFilledStar : RatingWidgetState("half_filled_stars")
    data object EmptyStars : RatingWidgetState("empty_star")
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        scale(scale = scaleFactor) {
            val canvasSize = this.size
            val left = (canvasSize.width / 2f) - (starPathBounds.width / 1.7f)
            val top = (canvasSize.height / 2f) - (starPathBounds.height / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = StarColor
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        scale(scale = scaleFactor) {
            val canvasSize = this.size
            val left = (canvasSize.width / 2f) - (starPathBounds.width / 1.7f)
            val top = (canvasSize.height / 2f) - (starPathBounds.height / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = StarColor,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        scale(scale = scaleFactor) {
            val canvasSize = this.size
            val left = (canvasSize.width / 2f) - (starPathBounds.width / 1.7f)
            val top = (canvasSize.height / 2f) - (starPathBounds.height / 1.7f)

            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightGray.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingWidgetFiveFilledStarsPreview() {
    RatingWidget(rating = 5.0)
}

@Preview(showBackground = true)
@Composable
fun RatingWidgetHalfFilledStarsPreview() {
    RatingWidget(rating = 3.7)
}

@Preview(showBackground = true)
@Composable
fun RatingWidgetZeroFilledStarsPreview() {
    RatingWidget(rating = 0.0)
}

@Preview(showBackground = true)
@Composable
fun FilledStarPreview() {
    val startPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    FilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 2f
    )
}

@Preview(showBackground = true)
@Composable
fun HalfFilledStarPreview() {
    val startPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    HalfFilledStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 2f
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyStarPreview() {
    val startPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    EmptyStar(
        starPath = starPath,
        starPathBounds = starPathBounds,
        scaleFactor = 2f
    )
}
