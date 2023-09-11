package com.example.musicapp.utils

import com.example.musicapp.data.model.Song

interface PlayerEvents {
    fun onPlayPauseClick()
    fun onPreviousClick()
    fun onNextClick()
    fun onTrackClick(track: Song)
    fun onSeekBarPositionChanged(position: Long)
}