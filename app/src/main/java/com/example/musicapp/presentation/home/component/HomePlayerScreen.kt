package com.example.musicapp.presentation.home.component

import android.graphics.BitmapFactory
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.home.model.PlayBackState
import com.example.musicapp.presentation.home.model.PlayerState
import com.example.musicapp.presentation.home.model.PlayerUIState
import com.example.musicapp.utils.PlayerStates
import com.example.musicapp.utils.animateViewWithHapticFeedback
import com.example.musicapp.utils.formatTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

@Composable
fun HomePlayerScreen(
    uiState: PlayerUIState,
    onDispose: () -> Unit,
    onSongPlayPauseClick: () -> Unit,
    onPrevSongClick: () -> Unit,
    onNextSongClick: () -> Unit,
    onSeekBarPositionChanged: (Long) -> Unit
) {
    val view = LocalView.current
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(uiState) {
        onDispose {
            onDispose()
        }
    }

    HomePlayerView(
        uiState = uiState,
        onSongPlayPauseClick = {
            view.animateViewWithHapticFeedback(offsetX, coroutineScope)
            onSongPlayPauseClick()
        },
        onPrevSongClick = {
            view.animateViewWithHapticFeedback(offsetX, coroutineScope)
            onPrevSongClick()
        },
        onNextSongClick = {
            view.animateViewWithHapticFeedback(offsetX, coroutineScope)
            onNextSongClick()
        },
        onSeekBarPositionChanged = {
            onSeekBarPositionChanged(it)
        }
    )

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomePlayerView(
    uiState: PlayerUIState,
    onSongPlayPauseClick: () -> Unit,
    onNextSongClick: () -> Unit,
    onPrevSongClick: () -> Unit,
    onSeekBarPositionChanged: (Long) -> Unit
) {
    val pagerState = rememberPagerState(uiState.playerState.selectedSongIndex)
    val scope = rememberCoroutineScope()
    var dominantColor by remember {
        mutableStateOf(Color.Black)
    }
    LaunchedEffect(key1 = uiState.playerState.selectedSongIndex) {
        scope.launch {
            pagerState.animateScrollToPage(uiState.playerState.selectedSongIndex)
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (pagerState.currentPage > uiState.playerState.selectedSongIndex) {
            onNextSongClick()
        } else if (pagerState.currentPage < uiState.playerState.selectedSongIndex) {
            onPrevSongClick()
        }

        scope.launch(
            context = Dispatchers.IO
        ) {
            val bitmap = BitmapFactory.decodeStream(URL("https://cms.samespace.com/assets/${uiState.selectedPlaylist[pagerState.currentPage].cover}").openStream())

            Palette.Builder(bitmap).generate {
                it?.let { palette ->
                    dominantColor = palette.darkVibrantSwatch?.rgb?.let { it1 ->
                        Color(
                            it1
                        )
                    } ?: Color(0xFF000000)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            dominantColor,
                            Color.Black,
                            Color.Black
                        )
                    )
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {

            HorizontalPager(
                pageCount = uiState.selectedPlaylist.size,
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 30.dp)
            ) {

                Image(
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .height(300.dp)
                        .size(50.dp),
                    painter =
                    rememberAsyncImagePainter(model = "https://cms.samespace.com/assets/${uiState.selectedPlaylist[it].cover}"),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 50.dp, start = 20.dp, end = 20.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    text = uiState.playerState.selectedSong?.name ?: "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    modifier = Modifier
                        .padding(top = 5.dp),
                    text = uiState.playerState.selectedSong?.artist ?: "",
                    color = Color(0xFF999898),
                    fontSize = 16.sp
                )
            }

            TrackProgressSlider(
                playbackStateValue = uiState.playerPlayBackState,
                onSeekBarPositionChanged = onSeekBarPositionChanged
            )

            ControlView(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 50.dp
                ),
                playerState = uiState.playerState,
                onSongPlayPauseClick = onSongPlayPauseClick,
                onNextSongClick = onNextSongClick,
                onPrevSongClick = onPrevSongClick
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackProgressSlider(
    playbackStateValue: PlayBackState,
    onSeekBarPositionChanged: (Long) -> Unit
) {
    var currentMediaProgress = playbackStateValue.currentPlaybackPosition.toFloat()
    var currentPosTemp by rememberSaveable { mutableStateOf(0f) }

    Slider(
        value = if (currentPosTemp == 0f) currentMediaProgress else currentPosTemp,
        onValueChange = { currentPosTemp = it },
        onValueChangeFinished = {
            currentMediaProgress = currentPosTemp
            currentPosTemp = 0f
            onSeekBarPositionChanged(currentMediaProgress.toLong())
        },
        valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        thumb = {},
        colors = SliderDefaults.colors(
            thumbColor = Color.Transparent,
            inactiveTrackColor = Color(0xFF999898),
            activeTrackColor = Color.White
        )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = playbackStateValue.currentPlaybackPosition.formatTime(),
            color = Color(0xFF999898),
            fontSize = 12.sp
        )
        Text(
            text = playbackStateValue.currentTrackDuration.formatTime(),
            color = Color(0xFF999898),
            fontSize = 12.sp
        )
    }
}

@Composable
fun ControlView(
    modifier: Modifier,
    playerState: PlayerState,
    onSongPlayPauseClick: () -> Unit,
    onNextSongClick: () -> Unit,
    onPrevSongClick: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onPrevSongClick()
                },
            painter = painterResource(id = R.drawable.ic_prev),
            contentDescription = null
        )
        if (playerState.playerState == PlayerStates.STATE_BUFFERING) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp),
                color = Color.White
            )
        } else {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onSongPlayPauseClick()
                    },
                painter = if (playerState.isPlaying) painterResource(id = R.drawable.ic_pause) else painterResource(
                    id = R.drawable.ic_play
                ),
                contentDescription = null
            )
        }

        Image(
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    onNextSongClick()
                },
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun PreviewPlayerView() {
    HomePlayerView(
        uiState = PlayerUIState(
            selectedPlaylist = listOf(
                Song(
                    id = 1,
                    status = "published",
                    sort = null,
                    userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
                    dateCreated = "2023-08-10T06:10:57.746Z",
                    userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
                    dateUpdated = "2023-08-10T07:19:48.547Z",
                    name = "Colors",
                    artist = "William King",
                    accent = "#331E00",
                    cover = "4f718272-6b0e-42ee-92d0-805b783cb471",
                    topTrack = true,
                    url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/august-145937.mp3"
                )
            )
        ),
        onNextSongClick = {},
        onPrevSongClick = {},
        onSongPlayPauseClick = {},
        onSeekBarPositionChanged = {}
    )
}

@Preview
@Composable
fun PreviewControlView() {
    ControlView(
        modifier = Modifier,
        playerState = PlayerState(),
        onNextSongClick = {},
        onPrevSongClick = {},
        onSongPlayPauseClick = {}
    )
}
