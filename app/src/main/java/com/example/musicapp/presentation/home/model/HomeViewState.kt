package com.example.musicapp.presentation.home.model

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.ViewEvent

data class HomeViewState(
    val uiState: HomeUIState,
    val selectedBottomType : BottomNavigationState = BottomNavigationState.FOR_YOU,
    val bottomTypeList : List<BottomNavigationState> = listOf(
        BottomNavigationState.FOR_YOU,
        BottomNavigationState.TOP_TRACKS
    )
) : ViewEvent

sealed class HomeUIState{
    object Loading : HomeUIState()
    object Error : HomeUIState()
    data class Success(
        val forYouList : List<Song> = emptyList(),
        val topTrackList : List<Song> = emptyList()
    ) : HomeUIState()
}