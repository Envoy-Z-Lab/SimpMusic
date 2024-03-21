package com.envoyz.riff.data.model.explore.mood.genre


import com.google.gson.annotations.SerializedName
import com.envoyz.riff.data.model.searchResult.songs.Thumbnail

data class Content(
    @SerializedName("playlistBrowseId")
    val playlistBrowseId: String,
    @SerializedName("thumbnail")
    val thumbnail: List<Thumbnail>?,
    @SerializedName("title")
    val title: Title
)