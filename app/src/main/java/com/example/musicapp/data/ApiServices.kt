package com.example.musicapp.data

import com.example.musicapp.data.model.SongsListModel
import com.example.musicapp.utils.ApiEndPoint
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET(ApiEndPoint.songs)
    suspend fun fetchSongs() : Response<SongsListModel>
}