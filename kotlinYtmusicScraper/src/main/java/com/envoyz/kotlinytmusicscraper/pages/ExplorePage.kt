package com.envoyz.kotlinytmusicscraper.pages

import com.envoyz.kotlinytmusicscraper.models.PlaylistItem
import com.envoyz.kotlinytmusicscraper.models.VideoItem

data class ExplorePage(
    val released: List<PlaylistItem>,
    val musicVideo: List<VideoItem>,
)
