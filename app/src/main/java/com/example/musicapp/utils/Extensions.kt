package com.example.musicapp.utils

import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.media3.common.MediaItem
import com.example.musicapp.data.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

fun List<Song>.toMediaItemList(): MutableList<MediaItem> {
    return this.map { MediaItem.fromUri(it.url!!) }.toMutableList()
}

val shakeKeyframes: AnimationSpec<Float> = keyframes {
    durationMillis = 800
    val easing = FastOutLinearInEasing

    for (i in 1..8) {
        val x = when (i % 3) {
            0 -> 4f
            1 -> -4f
            else -> 0f
        }
        x at durationMillis / 10 * i with easing
    }
}

fun View.animateViewWithHapticFeedback(
    offset: Animatable<Float, AnimationVector1D>,
    coroutineScope: CoroutineScope,
) {
    coroutineScope.launch {
        offset.animateTo(
            targetValue = 0f,
            animationSpec = shakeKeyframes,
        )
    }
    this.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            it.performHapticFeedback(HapticFeedbackConstants.REJECT)
        } else {
            it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
    }
}