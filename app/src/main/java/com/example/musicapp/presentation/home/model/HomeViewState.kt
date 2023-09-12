package com.example.musicapp.presentation.home.model

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.ViewEvent
import com.example.musicapp.utils.PlayerStates

data class HomeViewState(
    val uiState: HomeUIState,
    val selectedBottomType : BottomNavigationState = BottomNavigationState.FOR_YOU,
    val bottomTypeList : List<BottomNavigationState> = listOf(
        BottomNavigationState.FOR_YOU,
        BottomNavigationState.TOP_TRACKS
    ),
    val playerUIState: PlayerUIState = PlayerUIState()
) : ViewEvent

sealed class HomeUIState{
    object Loading : HomeUIState()
    object Error : HomeUIState()
    data class Success(
        val forYouList : List<Song> = emptyList(),
        val topTrackList : List<Song> = emptyList()
    ) : HomeUIState()
}
data class PlayerUIState(
    val selectedPlaylist : List<Song> = emptyList(),
    val playerState : PlayerState = PlayerState(),
    val playerPlayBackState: PlayBackState = PlayBackState(0,0)
)
data class PlayerState(
    val selectedSong: Song? = null,
    val isPlaying : Boolean = false,
    val selectedSongIndex : Int = -1,
    val playerState : PlayerStates = PlayerStates.STATE_IDLE
)

data class PlayBackState(
    val currentPlaybackPosition: Long,
    val currentTrackDuration: Long
)
