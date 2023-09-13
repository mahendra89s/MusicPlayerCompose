package com.example.musicapp.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.musicapp.data.model.Song
import com.example.musicapp.navigation.HandleNavigation
import com.example.musicapp.presentation.home.component.HomePlayerBottomView
import com.example.musicapp.presentation.home.component.HomePlayerScreen
import com.example.musicapp.presentation.home.component.HomePlayerView
import com.example.musicapp.presentation.home.component.SongListView
import com.example.musicapp.presentation.home.model.BottomNavigationState
import com.example.musicapp.presentation.home.model.HomeEvent
import com.example.musicapp.presentation.home.model.HomeUIState
import com.example.musicapp.presentation.home.model.HomeViewState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.state.collectAsState()

    HandleNavigation(navigationFlow = viewModel.navigation) {
        it.navigate(navController)
    }
    HomeScreenView(
        uiState = uiState,
        onSongClick = { song, selectedPlaylist ->
            viewModel.handleEvent(HomeEvent.OnSongClicked(song, selectedPlaylist))
        },
        onNextSongClick = {
            viewModel.handleEvent(HomeEvent.OnNextSongClick)
        },
        onPrevSongClick = {
            viewModel.handleEvent(HomeEvent.OnPrevSongClick)
        },
        onSeekBarPositionChanged = {
            viewModel.handleEvent(HomeEvent.OnSeekBarPositionChanged(it))
        },
        onSongPlayPauseClick = {
            viewModel.handleEvent(HomeEvent.OnSongPlayPause)
        }
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreenView(
    uiState: HomeViewState,
    onSongClick: (Song, List<Song>) -> Unit,
    onNextSongClick: () -> Unit,
    onPrevSongClick: () -> Unit,
    onSeekBarPositionChanged: (Long) -> Unit,
    onSongPlayPauseClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0)

    val fullScreenState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    ModalBottomSheetLayout(
        sheetContent = {
            if (uiState.playerUIState.playerState.selectedSong != null) {
                HomePlayerScreen(
                    uiState = uiState.playerUIState,
                    onNextSongClick = onNextSongClick,
                    onPrevSongClick = onPrevSongClick,
                    onSeekBarPositionChanged = onSeekBarPositionChanged,
                    onSongPlayPauseClick = onSongPlayPauseClick,
                    onDispose = {}
                )
            }
        },
        sheetState = fullScreenState,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetElevation = 8.dp
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()

        ) { paddingValues ->
            when (val state = uiState.uiState) {
                is HomeUIState.Success -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFF000000)
                            )
                            .padding(paddingValues)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            HorizontalPager(
                                pageCount = uiState.bottomTypeList.size,
                                state = pagerState
                            ) {
                                when (uiState.bottomTypeList[it]) {
                                    BottomNavigationState.FOR_YOU -> {
                                        SongListView(
                                            modifier = Modifier,
                                            songList = state.forYouList,
                                            isSongSelected = uiState.playerUIState.playerState.selectedSong!=null,
                                            onSongClick = {
                                                onSongClick(it, state.forYouList)
                                            }
                                        )
                                    }

                                    else -> {
                                        SongListView(
                                            modifier = Modifier,
                                            songList = state.topTrackList,
                                            isSongSelected = uiState.playerUIState.playerState.selectedSong!=null,

                                            onSongClick = {
                                                onSongClick(it, state.topTrackList)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            AnimatedVisibility(
                                visible = uiState.playerUIState.playerState.selectedSong != null,
                                enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight })
                            ) {
                                HomePlayerBottomView(
                                    modifier = Modifier,
                                    uiState = uiState.playerUIState,
                                    onSongPlayPauseClick = onSongPlayPauseClick,
                                    onViewClick = {
                                        scope.launch { fullScreenState.show() }
                                    }
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = if (uiState.playerUIState.playerState.selectedSong == null) {
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color.Transparent,
                                                    Color(0xFF000000),
                                                    Color(0xFF000000),
                                                    Color(0xFF000000)
                                                )
                                            )
                                        }else {
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(0xFF000000),
                                                    Color(0xFF000000)
                                                )
                                            )
                                        }
                                    ),
                                horizontalArrangement = Arrangement.SpaceAround,
                            ) {
                                (uiState.bottomTypeList.indices).forEach { index ->
                                    BottomNavigationItem(
                                        bottomNavigationState = uiState.bottomTypeList[index],
                                        selectedIndex = pagerState.currentPage,
                                        currentIndex = index,
                                        onClick = {
                                            scope.launch {
                                                pagerState.animateScrollToPage(index)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                is HomeUIState.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF000000)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFFFFFFF)
                        )
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF000000))
                    ) {

                    }

                }
            }
        }
    }

}

@Composable
fun BottomNavigationItem(
    bottomNavigationState: BottomNavigationState,
    selectedIndex: Int,
    currentIndex: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = bottomNavigationState.type,
            color = if (selectedIndex == currentIndex) Color.White else Color(0xFF999898),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Box(
            modifier = Modifier
                .size(5.dp)
                .background(
                    color = if (selectedIndex == currentIndex) Color.White else Color.Black
                )
                .clip(CircleShape)

        )
    }
}