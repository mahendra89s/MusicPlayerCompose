package com.example.musicapp.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.musicapp.data.model.Song


@Composable
fun SongListView(
    modifier: Modifier,
    songList: List<Song>,
    isSongSelected : Boolean,
    onSongClick: (Song) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp
            )
    ) {
        items(count = songList.size) {
            SongItemView(
                modifier = Modifier,
                song = songList[it],
                onSongClick = onSongClick
            )

            if(it == songList.size-1){
                val height = if(isSongSelected) 150.dp else 60.dp
                Spacer(modifier = Modifier.height(height))
            }
        }
    }
}

@Composable
fun SongItemView(
    modifier: Modifier,
    song: Song,
    onSongClick: (Song) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onSongClick(song)
            }
            .padding(top = 30.dp)
    ) {
        Image(
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                )
                .clip(CircleShape)
                .size(50.dp),
            painter =rememberAsyncImagePainter(model = "https://cms.samespace.com/assets/${song.cover}"),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Text(
                text = song.name ?: "",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = song.artist ?: "",
                color = Color(0xFF999898),
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewSongItemView() {
    SongItemView(
        modifier = Modifier,
        song = Song(
            accent = "",
            artist = "William King",
            cover = "",
            dateCreated = "",
            dateUpdated = "",
            id = 1,
            name = "Colors",
            sort = null,
            status = "",
            topTrack = null,
            url = "",
            userCreated = "",
            userUpdated = ""
        ),
        onSongClick = {}
    )
}