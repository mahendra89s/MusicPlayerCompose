package com.example.musicapp.data.datasource

import com.example.musicapp.data.model.SongsListModel
import com.example.musicapp.presentation.base.DataResult

interface SongsDataSource {
    suspend fun fetchSongs() : DataResult<SongsListModel>
}