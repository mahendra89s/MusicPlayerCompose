package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class SongsListModel(
    @SerializedName("data")
    val data: List<Song>
)