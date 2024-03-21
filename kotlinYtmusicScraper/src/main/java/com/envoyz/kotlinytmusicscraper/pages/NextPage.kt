package com.envoyz.kotlinytmusicscraper.pages

import com.envoyz.kotlinytmusicscraper.models.Album
import com.envoyz.kotlinytmusicscraper.models.Artist
import com.envoyz.kotlinytmusicscraper.models.BrowseEndpoint
import com.envoyz.kotlinytmusicscraper.models.PlaylistPanelVideoRenderer
import com.envoyz.kotlinytmusicscraper.models.SongItem
import com.envoyz.kotlinytmusicscraper.models.WatchEndpoint
import com.envoyz.kotlinytmusicscraper.models.oddElements
import com.envoyz.kotlinytmusicscraper.models.splitBySeparator
import com.envoyz.kotlinytmusicscraper.utils.parseTime

data class NextResult(
    val title: String? = null,
    val items: List<SongItem>,
    val currentIndex: Int? = null,
    val lyricsEndpoint: BrowseEndpoint? = null,
    val relatedEndpoint: BrowseEndpoint? = null,
    val continuation: String?,
    val endpoint: WatchEndpoint, // current or continuation next endpoint
)

object NextPage {
    fun fromPlaylistPanelVideoRenderer(renderer: PlaylistPanelVideoRenderer): SongItem? {
        val longByLineRuns = renderer.longBylineText?.runs?.splitBySeparator() ?: return null
        return SongItem(
            id = renderer.videoId ?: return null,
            title = renderer.title?.runs?.firstOrNull()?.text ?: return null,
            artists = longByLineRuns.firstOrNull()?.oddElements()?.map {
                Artist(
                    name = it.text,
                    id = it.navigationEndpoint?.browseEndpoint?.browseId
                )
            } ?: return null,
            album = longByLineRuns.getOrNull(1)?.firstOrNull()?.takeIf {
                it.navigationEndpoint?.browseEndpoint != null
            }?.let {
                Album(
                    name = it.text,
                    id = it.navigationEndpoint?.browseEndpoint?.browseId!!
                )
            },
            duration = renderer.lengthText?.runs?.firstOrNull()?.text?.parseTime() ?: return null,
            thumbnail = renderer.thumbnail.thumbnails.lastOrNull()?.url ?: return null,
            explicit = renderer.badges?.find {
                it.musicInlineBadgeRenderer.icon.iconType == "MUSIC_EXPLICIT_BADGE"
            } != null,
            thumbnails = renderer.thumbnail
        )
    }
}
