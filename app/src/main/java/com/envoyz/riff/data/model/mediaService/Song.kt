package com.envoyz.riff.data.model.mediaService

import android.graphics.Bitmap
import com.envoyz.riff.data.model.searchResult.songs.Album
import com.envoyz.riff.data.model.searchResult.songs.Artist
import com.envoyz.riff.data.model.searchResult.songs.Thumbnail

data class Song (
    val title: String?,
    val artists: List<Artist>?,
    val duration: Long,
    val lyrics: Any,
    val album: Album,
    val videoId: String,
    val thumbnail: Thumbnail?,
    val thumbnailBitmap: Bitmap?,
    val isLocal: Boolean
)