package com.example.musicapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapp.data.model.Song
import com.example.musicapp.domain.usecase.FetchSongsUseCase
import com.example.musicapp.navigation.AppNavigationGraphRoute
import com.example.musicapp.navigation.NavigationState
import com.example.musicapp.presentation.base.DataResult
import com.example.musicapp.presentation.home.model.HomeEvent
import com.example.musicapp.presentation.home.model.HomeUIState
import com.example.musicapp.presentation.home.model.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchSongsUseCase: FetchSongsUseCase
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
                    navigate(
                        NavigationState.NavigateToDestination(
                            AppNavigationGraphRoute.PlayerFragment,
                            /*argsValues = arrayOf(
                                listOf(
                                    event.song,
                                    (state.value.uiState as HomeUIState.Success).forYouList
                                )
                            )*/
                        )
                    )
                }
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