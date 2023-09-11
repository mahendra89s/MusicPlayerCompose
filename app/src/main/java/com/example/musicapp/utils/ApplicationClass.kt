package com.example.musicapp.utils

import android.app.Application
import com.example.musicapp.data.model.Song
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {

    lateinit var topTrackSongList : MutableList<Song>
    lateinit var forYouSongList : MutableList<Song>

    override fun onCreate() {
        super.onCreate()
        topTrackSongList = mutableListOf()
        forYouSongList = mutableListOf()
    }
}