package com.lalic.marko.mymenu.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lalic.marko.mymenu.R

const val OFFLINE_ANIMATION_TEST_TAG = "offline-anim-test-tag"

@Composable
fun OfflineScreen() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.nointernetlottie))

    LottieAnimation(
        modifier = Modifier.testTag(OFFLINE_ANIMATION_TEST_TAG),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}

@Preview
@Composable
fun PreviewOfflineScreen() {
    OfflineScreen()
}