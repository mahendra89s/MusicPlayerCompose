package com.example.musicapp.data.datasource

import com.example.musicapp.data.ApiServices
import com.example.musicapp.data.model.SongsListModel
import com.example.musicapp.presentation.base.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class SongsDataSourceImpl @Inject constructor(
    retrofit: Retrofit
): SongsDataSource {

    private val songsApiService by lazy { retrofit.create(ApiServices::class.java) }

    override suspend fun fetchSongs(): DataResult<SongsListModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = songsApiService.fetchSongs()
                if (response.isSuccessful) {
                    DataResult.Success(data = response.body()!!)
                } else {
                    DataResult.Error(response.message())
                }
            } catch (exception: Exception) {
                DataResult.Error(exception.message?:"")
            }
        }
    }
}