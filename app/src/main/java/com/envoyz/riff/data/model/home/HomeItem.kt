package com.envoyz.riff.data.model.home


import com.google.gson.annotations.SerializedName
import com.envoyz.riff.data.model.searchResult.songs.Thumbnail

data class HomeItem(
    @SerializedName("contents")
    val contents: List<Content?>,
    @SerializedName("title")
    val title: String,
    val subtitle: String? = null,
    val thumbnail: List<Thumbnail>? = null,
    val channelId: String? = null,
)