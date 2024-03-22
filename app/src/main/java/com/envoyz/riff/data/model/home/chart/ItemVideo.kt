package com.envoyz.riff.data.model.home.chart


import com.google.gson.annotations.SerializedName
import com.envoyz.riff.data.model.browse.album.Track
import com.envoyz.riff.data.model.searchResult.songs.Artist
import com.envoyz.riff.data.model.searchResult.songs.Thumbnail

data class ItemVideo(
    @SerializedName("artists")
    val artists: List<Artist>?,
    @SerializedName("playlistId")
    val playlistId: String,
    @SerializedName("thumbnails")
    val thumbnails: List<Thumbnail>,
    @SerializedName("title")
    val title: String,
    @SerializedName("videoId")
    val videoId: String,
    @SerializedName("views")
    val views: String
)
fun ItemVideo.toTrack(): Track {
    return Track(
        album = null,
        artists = artists,
        duration = "",
        durationSeconds = 0,
        isAvailable = false,
        isExplicit = false,
        likeStatus = "INDIFFERENT",
        thumbnails = thumbnails,
        title = title,
        videoId = videoId,
        videoType = "",
        category = null,
        feedbackTokens = null,
        resultType = null,
        year = ""

    )
}