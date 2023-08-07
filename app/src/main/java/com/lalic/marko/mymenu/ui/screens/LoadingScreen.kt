package com.lalic.marko.mymenu.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalic.marko.mymenu.DataManager
import com.lalic.marko.mymenu.SharedViewModel

private const val SPINNER_START_ANGLE = 135f
private const val SPINNER_END_ANGLE = 495f
private const val ANIMATION_DURATION_IN_MILLIS = 1500
private const val SPINNER_PERCENT = 0.25f
const val PROGRESS_INDICATOR = "progress_indicator"

private val SpinnerStrokeWidth = 5.dp
private val SpinnerSize = 44.dp

@Composable
fun LoadingScreen(viewModel: SharedViewModel, onLoadingCompleted: () -> Unit) {

    val context = LocalContext.current

    Box(contentAlignment = Alignment.Center) {
        val infiniteTransition = rememberInfiniteTransition()
        val angle by infiniteTransition.animateFloat(
            initialValue = SPINNER_START_ANGLE,
            targetValue = SPINNER_END_ANGLE,
            animationSpec = infiniteRepeatable(
                animation = tween(ANIMATION_DURATION_IN_MILLIS, easing = FastOutSlowInEasing)
            )
        )
        CircularProgressIndicator(
            modifier = Modifier
                .testTag(PROGRESS_INDICATOR)
                .rotate(angle)
                .size(SpinnerSize)
                .drawBehind {
                    drawCircle(
                        color = Color.Companion.White,
                        radius = size.width / 2 - SpinnerStrokeWidth.toPx() / 2,
                        style = Stroke(SpinnerStrokeWidth.toPx())
                    )
                },
            color = Color.Blue,
            strokeWidth = SpinnerStrokeWidth,
            progress = SPINNER_PERCENT
        )
    }

    LaunchedEffect(Unit) {
        val dataManager = DataManager()
        val venues = dataManager.fetchVenues(context, forceUpdate = false)
        viewModel.postValues(venues)
        onLoadingCompleted()
    }
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    val viewModel = SharedViewModel()
    LoadingScreen(viewModel = viewModel, onLoadingCompleted = {

    })
}