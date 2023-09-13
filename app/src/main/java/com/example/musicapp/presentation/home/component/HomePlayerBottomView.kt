package com.example.musicapp.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.R
import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.home.model.PlayerState
import com.example.musicapp.presentation.home.model.PlayerUIState
import com.example.musicapp.utils.PlayerStates

@Composable
fun HomePlayerBottomView(
    modifier: Modifier,
    uiState: PlayerUIState,
    onSongPlayPauseClick: () -> Unit,
    onViewClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF201A18)
            )
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clickable { onViewClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                )
                .clip(CircleShape)
                .size(50.dp),
            painter = rememberAsyncImagePainter(model = "https://cms.samespace.com/assets/${uiState.playerState.selectedSong?.cover}"),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            text = uiState.playerState.selectedSong?.name ?: "",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        if (uiState.playerState.playerState == PlayerStates.STATE_BUFFERING) {
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
                painter = if (uiState.playerState.isPlaying) painterResource(id = R.drawable.ic_pause) else painterResource(
                    id = R.drawable.ic_play
                ),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomePlayerBottomView() {
    HomePlayerBottomView(
        modifier = Modifier,
        uiState = PlayerUIState(
            playerState = PlayerState(
                selectedSong = Song(
                    accent = "#59123F",
                    artist = "MattDawson",
                    cover = "c296c57b-1a5a-45a7-80a7-3f60f990ed62",
                    dateCreated = "2023-08-10T06: 12: 09.978Z",
                    dateUpdated = "2023-08-10T07: 20: 42.673Z",
                    id = 3,
                    name = "FallenLeaves",
                    sort = null,
                    status = "published",
                    topTrack = false,
                    url = "https: //pub-172b4845a7e24a16956308706aaf24c2.r2.dev/french-song-about-brittany-136020.mp3",
                    userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
                    userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
                )
            )
        ),
        onSongPlayPauseClick = {},
        onViewClick = {}
    )
}