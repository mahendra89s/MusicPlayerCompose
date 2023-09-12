package com.example.musicapp.presentation.home.model

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.ViewEvent
import com.example.musicapp.presentation.player.model.PlayerEvent

sealed class HomeEvent : ViewEvent {
    object OnScreenLoad : HomeEvent()
    data class OnSongClicked(
        val song : Song,
        val selectedPlaylist : List<Song>
    ) : HomeEvent()

    object OnSongPlayPause : HomeEvent()

    object OnNextSongClick : HomeEvent()

    object OnPrevSongClick : HomeEvent()

    data class OnSeekBarPositionChanged(
        val position: Long
    ) : HomeEvent()

    object OnDispose : HomeEvent()
}