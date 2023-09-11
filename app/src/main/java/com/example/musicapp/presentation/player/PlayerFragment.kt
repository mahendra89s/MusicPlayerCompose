package com.example.musicapp.presentation.player

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.BaseComposeFragment
import com.example.musicapp.presentation.player.model.PlayerEvent
import com.example.musicapp.presentation.player.model.provideSongList
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlayerFragment : BaseComposeFragment<PlayerEvent>() {

    private val viewModel: PlayerViewModel by viewModels()

    @Composable
    override fun Screen() {
        PlayerScreen(viewModel = viewModel, navController = findNavController())
    }

    override fun getArgs() {
        //val selectedSong = arguments?.getParcelable<Song>(AppNavigationGraphArguments.SELECTED_SONG)
        val selectedSong = Song(
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
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/french-song-about-brittany-136020.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        )

        viewModel.handleEvent(
            PlayerEvent.OnArgumentReceived(
                id = selectedSong.id!!,
                songList = provideSongList()
            )
        )
    }
}