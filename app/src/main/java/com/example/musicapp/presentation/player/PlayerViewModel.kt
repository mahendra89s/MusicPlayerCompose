package com.example.musicapp.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.navigation.NavigationState
import com.example.musicapp.presentation.player.model.PlayBackState
import com.example.musicapp.presentation.player.model.PlayerEvent
import com.example.musicapp.presentation.player.model.PlayerViewState
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
class PlayerViewModel @Inject constructor(
    private val player: MyPlayer
) : ViewModel() {
    private val _state: MutableStateFlow<PlayerViewState> = MutableStateFlow(
        PlayerViewState()
    )

    val state: StateFlow<PlayerViewState> = _state.asStateFlow()

    private val _navigation = MutableSharedFlow<NavigationState>()
    val navigation: SharedFlow<NavigationState> = _navigation

    fun handleEvent(
        event: PlayerEvent
    ) {
        when (event) {
            is PlayerEvent.OnArgumentReceived -> {
                val selectedSong = event.songList.find { it.id == event.id }
                setState {
                    copy(
                        songs = event.songList,
                        playerState = playerState.copy(
                            selectedSong = selectedSong,
                            isPlaying = true,
                            selectedSongIndex = event.songList.indexOf(selectedSong)
                        )
                    )
                }

                player.iniPlayer(event.songList.toMediaItemList())
                observePlayerState()
            }

            is PlayerEvent.OnSongPlayPause -> {
                player.playPause()
            }

            is PlayerEvent.OnNextSongClick -> {
                onTrackSelected(state.value.playerState.selectedSongIndex + 1)
            }

            is PlayerEvent.OnPrevSongClick -> {
                onTrackSelected(state.value.playerState.selectedSongIndex - 1)
            }

            is PlayerEvent.OnSeekBarPositionChanged -> {
                onSeekBarPositionChanged(event.position)
            }
            is PlayerEvent.OnDispose -> {
                player.releasePlayer()
            }
        }
    }

    private fun onTrackSelected(index: Int) {
        if (index != -1 && index != state.value.songs.size) {
            setState {
                copy(
                    playerState = playerState.copy(
                        selectedSongIndex = index,
                        selectedSong = state.value.songs.find { it.id == index+1 }
                    )
                )
            }
            player.setUpTrack(index, state.value.playerState.isPlaying)
        }
    }

    private fun updateState(playerState1: PlayerStates) {
        if (state.value.playerState.selectedSongIndex != -1 && state.value.playerState.selectedSongIndex != state.value.songs.size) {
            setState {
                copy(
                    playerState = playerState.copy(
                        isPlaying = playerState1 == PlayerStates.STATE_PLAYING,
                        selectedSong = state.value.songs[state.value.playerState.selectedSongIndex]
                    )
                )
            }
            updatePlaybackState(playerState1)
            if (playerState1 == PlayerStates.STATE_NEXT_TRACK) {
                onTrackSelected(state.value.playerState.selectedSongIndex + 1)
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
                        playerPlayBackState = PlayBackState(
                            currentPlaybackPosition = player.currentPlaybackPosition,
                            currentTrackDuration = player.currentTrackDuration
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
        newState: PlayerViewState.() -> PlayerViewState
    ) {
        _state.update { it.newState() }
    }
}