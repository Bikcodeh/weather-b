package com.bikcodeh.weatherapp.core.designsystem.component

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

/** Decorative Lottie animation loaded from [animationRes] that loops forever. */
@Composable
fun LoopingLottieAnimation(@RawRes animationRes: Int, modifier: Modifier = Modifier) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    LottieAnimation(
        composition = composition.value,
        modifier = modifier,
        iterations = LottieConstants.IterateForever,
    )
}
