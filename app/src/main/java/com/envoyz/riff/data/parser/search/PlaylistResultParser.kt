package com.envoyz.riff.data.parser.search

import com.envoyz.kotlinytmusicscraper.pages.SearchResult
import com.envoyz.riff.data.model.searchResult.playlists.PlaylistsResult
import com.envoyz.riff.data.model.searchResult.songs.Thumbnail

fun parseSearchPlaylist(result: SearchResult): ArrayList<PlaylistsResult> {
    val playlistsResult: ArrayList<PlaylistsResult> = arrayListOf()
    result.items.forEach {
        val playlist = it as com.envoyz.kotlinytmusicscraper.models.PlaylistItem
        playlistsResult.add(
            PlaylistsResult(
                author = playlist.author?.name ?: "",
                browseId = playlist.id,
                category = "playlist",
                itemCount = playlist.songCountText ?: "",
                resultType = "Playlist",
                thumbnails = listOf(
                    Thumbnail(
                        544,
                        if (playlist.thumbnail.contains(Regex("([wh])120"))) Regex("([wh])120").replace(
                            playlist.thumbnail,
                            "$1544"
                        ) else playlist.thumbnail,
                        544
                    )
                ),
                title = playlist.title
            )
        )
    }
    return playlistsResult
}