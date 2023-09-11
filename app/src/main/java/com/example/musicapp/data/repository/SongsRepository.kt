package com.example.musicapp.data.repository

import com.example.musicapp.data.model.SongsListModel
import com.example.musicapp.presentation.base.DataResult

interface SongsRepository {
    suspend fun fetchSongs() : DataResult<SongsListModel>
}