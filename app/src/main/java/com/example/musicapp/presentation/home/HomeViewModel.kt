package com.example.musicapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Song
import com.example.musicapp.domain.usecase.FetchSongsUseCase
import com.example.musicapp.navigation.NavigationState
import com.example.musicapp.presentation.base.DataResult
import com.example.musicapp.presentation.home.model.HomeEvent
import com.example.musicapp.presentation.home.model.HomeUIState
import com.example.musicapp.presentation.home.model.HomeViewState
import com.example.musicapp.presentation.home.model.PlayBackState
import com.example.musicapp.utils.MyPlayer
import com.example.musicapp.utils.PlayerStates
import com.example.musicapp.utils.toMediaItemList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchSongsUseCase: FetchSongsUseCase,
    private val player: MyPlayer
) : ViewModel() {

    private val _state: MutableStateFlow<HomeViewState> = MutableStateFlow(
        HomeViewState(
            uiState = HomeUIState.Loading
        )
    )

    val state: StateFlow<HomeViewState> = _state.asStateFlow()

    private val _navigation = MutableSharedFlow<NavigationState>()
    val navigation: SharedFlow<NavigationState> = _navigation

    fun handleEvent(
        event: HomeEvent
    ) {
        when (event) {
            is HomeEvent.OnScreenLoad -> {
                fetchSongs()
            }

            is HomeEvent.OnSongClicked -> {
                if (state.value.uiState is HomeUIState.Success) {
                    setState {
                        copy(
                            playerUIState = playerUIState.copy(
                                selectedPlaylist = event.selectedPlaylist,
                                playerState = playerUIState.playerState.copy(
                                    selectedSong = event.song,
                                    isPlaying = true,
                                    selectedSongIndex = event.selectedPlaylist.indexOf(event.song),
                                )
                            )
                        )
                    }

                    player.iniPlayer(event.selectedPlaylist.toMediaItemList())
                    onTrackSelected(state.value.playerUIState.playerState.selectedSongIndex)
                    observePlayerState()
                }
            }

            is HomeEvent.OnSongPlayPause -> {
                player.playPause()
            }

            is HomeEvent.OnNextSongClick -> {
                onTrackSelected(state.value.playerUIState.playerState.selectedSongIndex + 1)
            }

            is HomeEvent.OnPrevSongClick -> {
                onTrackSelected(state.value.playerUIState.playerState.selectedSongIndex - 1)
            }

            is HomeEvent.OnSeekBarPositionChanged -> {
                onSeekBarPositionChanged(event.position)
            }

            is HomeEvent.OnDispose -> {
                player.releasePlayer()
            }
        }
    }

    private fun fetchSongs() {
        viewModelScope.launch {
            fetchSongsUseCase.process().collect {
                when (it) {
                    is DataResult.Success -> {
                        setState {
                            copy(
                                uiState = HomeUIState.Success(
                                    forYouList = it.data,
                                    topTrackList = filterTopTracks(it.data)
                                )
                            )
                        }
                    }

                    is DataResult.Error -> {
                        setState {
                            copy(
                                uiState = HomeUIState.Error
                            )
                        }
                    }
                }
            }
        }
    }

    private fun filterTopTracks(songs: List<Song>): List<Song> {
        return songs.filter { it.topTrack == true }
    }

    private fun onTrackSelected(index: Int) {
        if (index != -1 && index != state.value.playerUIState.selectedPlaylist.size) {
            setState {
                copy(
                    playerUIState = playerUIState.copy(
                        playerState = playerUIState.playerState.copy(
                            selectedSongIndex = index,
                            selectedSong = state.value.playerUIState.selectedPlaylist.find { it.id == index + 1 }
                        )
                    )
                )
            }
            player.setUpTrack(index, state.value.playerUIState.playerState.isPlaying)
        }
    }

    private fun updateState(playerState1: PlayerStates) {
        if (state.value.playerUIState.playerState.selectedSongIndex != -1 && state.value.playerUIState.playerState.selectedSongIndex != state.value.playerUIState.selectedPlaylist.size) {
            setState {
                copy(
                    playerUIState = playerUIState.copy(
                        playerState = playerUIState.playerState.copy(
                            isPlaying = playerState1 == PlayerStates.STATE_PLAYING,
                            selectedSong = state.value.playerUIState.selectedPlaylist[state.value.playerUIState.playerState.selectedSongIndex],
                            playerState = playerState1
                        )
                    )
                )
            }
            updatePlaybackState(playerState1)
            if (playerState1 == PlayerStates.STATE_NEXT_TRACK) {
                onTrackSelected(state.value.playerUIState.playerState.selectedSongIndex + 1)
            }
            if (playerState1 == PlayerStates.STATE_END) {
                onTrackSelected(0)
            }
        }
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            player.playerState.collect {
                updateState(it)
            }
        }
    }

    private fun updatePlaybackState(playerStates: PlayerStates) {
        viewModelScope.launch {
            do {
                setState {
                    copy(
                        playerUIState = playerUIState.copy(
                            playerPlayBackState = PlayBackState(
                                currentPlaybackPosition = player.currentPlaybackPosition,
                                currentTrackDuration = player.currentTrackDuration
                            )
                        )
                    )
                }
                delay(1000)
            } while (playerStates == PlayerStates.STATE_PLAYING && isActive)
        }
    }

    private fun onSeekBarPositionChanged(position: Long) {
        viewModelScope.launch { player.seekToPosition(position) }
    }

    private fun navigate(
        navigationState: NavigationState
    ) {
        viewModelScope.launch {
            _navigation.emit(navigationState)
        }
    }

    private fun setState(
        newState: HomeViewState.() -> HomeViewState
    ) {
        _state.update { it.newState() }
    }
}