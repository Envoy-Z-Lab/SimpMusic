package com.envoyz.kotlinytmusicscraper.pages

import com.envoyz.kotlinytmusicscraper.models.Album
import com.envoyz.kotlinytmusicscraper.models.AlbumItem
import com.envoyz.kotlinytmusicscraper.models.Artist
import com.envoyz.kotlinytmusicscraper.models.MusicResponsiveListItemRenderer
import com.envoyz.kotlinytmusicscraper.models.SongItem
import com.envoyz.kotlinytmusicscraper.models.Thumbnails
import com.envoyz.kotlinytmusicscraper.models.oddElements
import com.envoyz.kotlinytmusicscraper.utils.parseTime

data class AlbumPage(
    val album: AlbumItem,
    val songs: List<SongItem>,
    val description: String?,
    val thumbnails: Thumbnails?,
    val duration: String?,
) {
    companion object {
        fun fromMusicResponsiveListItemRenderer(renderer: MusicResponsiveListItemRenderer?): SongItem? {
            if (renderer == null) return null
            else {
                return SongItem(
                    id = renderer.playlistItemData?.videoId ?: return null,
                    title = renderer.flexColumns.firstOrNull()
                        ?.musicResponsiveListItemFlexColumnRenderer?.text?.runs
                        ?.firstOrNull()?.text ?: return null,
                    artists = renderer.flexColumns.getOrNull(1)?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.oddElements()
                        ?.map {
                            Artist(
                                name = it.text,
                                id = it.navigationEndpoint?.browseEndpoint?.browseId
                            )
                        } ?: return null,
                    album = renderer.flexColumns.getOrNull(2)?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.firstOrNull()
                        ?.let {
                            Album(
                                name = it.text,
                                id = it.navigationEndpoint?.browseEndpoint?.browseId!!
                            )
                        } ?: return null,
                    duration = renderer.fixedColumns?.firstOrNull()
                        ?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.firstOrNull()
                        ?.text?.parseTime() ?: return null,
                    thumbnail = renderer.thumbnail?.musicThumbnailRenderer?.getThumbnailUrl()
                        ?: return null,
                    thumbnails = renderer.thumbnail.musicThumbnailRenderer.thumbnail,
                    explicit = renderer.badges?.find {
                        it.musicInlineBadgeRenderer.icon.iconType == "MUSIC_EXPLICIT_BADGE"
                    } != null
                )
            }
        }
    }
}