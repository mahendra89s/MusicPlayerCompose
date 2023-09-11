package com.example.musicapp.utils

import android.media.session.PlaybackState
import androidx.media3.common.MediaItem
import androidx.media3.extractor.mp4.Track
import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.player.model.PlayBackState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
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

fun CoroutineScope.collectPlayerState(
    myPlayer: MyPlayer, updateState: (PlayerStates) -> Unit
) {
    this.launch {
        myPlayer.playerState.collect {
            updateState(it)
        }
    }
}


fun CoroutineScope.launchPlaybackStateJob(
    playbackStateFlow: MutableStateFlow<PlayBackState>, state: PlayerStates, myPlayer: MyPlayer
) = launch {
    do {
        playbackStateFlow.emit(
            PlayBackState(
                currentPlaybackPosition = myPlayer.currentPlaybackPosition,
                currentTrackDuration = myPlayer.currentTrackDuration
            )
        )
        delay(1000) // delay for 1 second
    } while (state == PlayerStates.STATE_PLAYING && isActive)
}