package com.envoyz.riff.data.model.browse.artist

import com.envoyz.riff.data.model.searchResult.songs.Thumbnail

data class ResultPlaylist(
    val id: String,
    val author: String,
    val thumbnails: List<Thumbnail>,
    val title: String,
) {
}