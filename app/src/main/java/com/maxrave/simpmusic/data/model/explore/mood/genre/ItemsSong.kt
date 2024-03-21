package com.envoyz.riff.data.model.explore.mood.genre

import com.envoyz.riff.data.model.searchResult.songs.Artist

data class ItemsSong(
    val title: String,
    val artist: List<Artist>?,
    val videoId: String,
)
