package com.example.musicapp.presentation.player.model

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.ViewEvent

sealed class PlayerEvent : ViewEvent {

    data class OnArgumentReceived(
        val id: Int,
        val songList: List<Song>,
    ) : PlayerEvent()

    object OnSongPlayPause : PlayerEvent()

    object OnNextSongClick : PlayerEvent()

    object OnPrevSongClick : PlayerEvent()

    data class OnSeekBarPositionChanged(
        val position: Long
    ) : PlayerEvent()

    object OnDispose : PlayerEvent()

}