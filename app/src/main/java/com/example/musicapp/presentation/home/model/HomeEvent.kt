package com.example.musicapp.presentation.home.model

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.ViewEvent

sealed class HomeEvent : ViewEvent {
    object OnScreenLoad : HomeEvent()
    data class OnSongClicked(
        val song : Song
    ) : HomeEvent()
}