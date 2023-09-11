package com.example.musicapp.domain.usecase

import com.example.musicapp.data.model.Song
import com.example.musicapp.data.repository.SongsRepository
import com.example.musicapp.presentation.base.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchSongsUseCase @Inject constructor(
    private val repository: SongsRepository
) {
    suspend fun process() : Flow<DataResult<List<Song>>> = flow{
       when(val apiData = repository.fetchSongs()){
            is DataResult.Success -> {
                emit(DataResult.Success(apiData.data.data))
            }
            is DataResult.Error -> {
                emit(apiData)
            }
        }
    }
}