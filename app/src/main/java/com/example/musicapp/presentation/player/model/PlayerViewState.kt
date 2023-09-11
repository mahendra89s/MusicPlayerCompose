package com.example.musicapp.presentation.player.model

import com.example.musicapp.data.model.Song
import com.example.musicapp.presentation.base.ViewState

data class PlayerViewState(
    val songs: List<Song> = provideSongList(),
    val playerState : PlayerState = PlayerState(),
    val playerPlayBackState: PlayBackState = PlayBackState(0,0)
) : ViewState

data class PlayerState(
    val selectedSong: Song? = Song(
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
    ),
    val isPlaying : Boolean = false,
    val selectedSongIndex : Int = -1,
)

data class PlayBackState(
    val currentPlaybackPosition: Long,
    val currentTrackDuration: Long
)

fun provideSongList(): List<Song> {
    return listOf(
        Song(
            accent = "#331E00",
            artist = "WilliamKing",
            cover = "4f718272-6b0e-42ee-92d0-805b783cb471",
            dateCreated = "2023-08-10T06: 10: 57.746Z",
            dateUpdated = "2023-08-10T07: 19: 48.547Z",
            id = 1,
            name = "Colors",
            sort = null,
            status = "published",
            topTrack = true,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/august-145937.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#0A092F",
            artist = "Markotopa",
            cover = "e714a3b8-9ae0-4417-a2d1-6ece39ad5776",
            dateCreated = "2023-08-10T06: 11: 31.021Z",
            dateUpdated = "2023-08-10T07: 23: 07.983Z",
            id = 2,
            name = "August",
            sort = null,
            status = "published",
            topTrack = false,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/phoenix-97462.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
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
        ),
        Song(
            accent = "#0B565B",
            artist = "AdamSmith",
            cover = "9fffafe9-9013-4846-8b5c-2b5dcbcd4b62",
            dateCreated = "2023-08-10T06: 12: 51.670Z",
            dateUpdated = "2023-08-10T07: 21: 12.661Z",
            id = 4,
            name = "November",
            sort = null,
            status = "published",
            topTrack = false,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/perfect-timing-by-saavane-sweet-funky-song-155314.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#001D61",
            artist = "Saavane",
            cover = "62e1d9b2-593b-4cf0-938d-79e660d7ac25",
            dateCreated = "2023-08-10T06: 14: 04.585Z",
            dateUpdated = "2023-08-10T07: 21: 33.125Z",
            id = 5,
            name = "TheFrench",
            sort = null,
            status = "published",
            topTrack = false,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/uplifting-corporate-pop-rock-it-is-time-113871.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#033435",
            artist = "Saavane",
            cover = "38b58de6-33aa-4e9d-beb2-46d8d81b1540",
            dateCreated = "2023-08-10T06: 14: 41.564Z",
            dateUpdated = "2023-08-10T07: 21: 53.614Z",
            id = 6,
            name = "PerfectTiming",
            sort = null,
            status = "published",
            topTrack = true,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/first-touch-160603.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#331A05",
            artist = "TomKeen",
            cover = "0083048f-5fd8-47fd-9013-6d340151b345",
            dateCreated = "2023-08-10T06: 15: 14.439Z",
            dateUpdated = "2023-08-10T07: 22: 26.020Z",
            id = 7,
            name = "Uplift",
            sort = null,
            status = "published",
            topTrack = true,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/sunflowers-spring-and-summer-piano-music-14010.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#332B05",
            artist = "WilliamKing",
            cover = "bb7c91a1-a2cb-42ae-b9a9-dee679c8726e",
            dateCreated = "2023-08-10T06: 15: 44.766Z",
            dateUpdated = "2023-08-10T07: 22: 48.401Z",
            id = 8,
            name = "FirstTouch",
            sort = null,
            status = "published",
            topTrack = true,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/illusion-feel-ambient-guitar-146100.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#57001A",
            artist = "SarahTaylor",
            cover = "2d8f1cca-0e1b-416b-87ce-50ff2023-09-1016: 42: 45.906AndroidRuntimeE50cdac4f",
            dateCreated = "2023-08-10T06: 16: 14.372Z",
            dateUpdated = "2023-08-10T07: 17: 05.921Z",
            id = 9,
            name = "Sunflowers",
            sort = null,
            status = "published",
            topTrack = true,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2-r2-dev-phoenix-97462-mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        ),
        Song(
            accent = "#160D5E",
            artist = "WilliamKing",
            cover = "c26001ae-c51a-43ba-b309-f1fae226ef40",
            dateCreated = "2023-08-10T06: 16: 38.957Z",
            dateUpdated = "2023-08-10T07: 23: 26.359Z",
            id = 10,
            name = "IllusionFeel",
            sort = null,
            status = "published",
            topTrack = true,
            url = "https://pub-172b4845a7e24a16956308706aaf24c2.r2.dev/great-is-thy-faithfulness-9449.mp3",
            userCreated = "2085be13-8079-40a6-8a39-c3b9180f9a0a",
            userUpdated = "2085be13-8079-40a6-8a39-c3b9180f9a0a"
        )
    )
}