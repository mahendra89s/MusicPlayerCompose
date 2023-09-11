package com.example.musicapp.data.repository

import com.example.musicapp.data.datasource.SongsDataSource
import com.example.musicapp.data.model.SongsListModel
import com.example.musicapp.presentation.base.DataResult
import javax.inject.Inject

class SongsRepositoryImpl @Inject constructor(
    private val songsDataSource: SongsDataSource
) : SongsRepository {

    override suspend fun fetchSongs(): DataResult<SongsListModel> {
        return songsDataSource.fetchSongs()
    }

}