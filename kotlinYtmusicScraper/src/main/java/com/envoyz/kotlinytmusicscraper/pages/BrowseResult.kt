package com.envoyz.kotlinytmusicscraper.pages

import com.envoyz.kotlinytmusicscraper.models.YTItem

data class BrowseResult(
    val title: String?,
    val items: List<Item>,
) {
    data class Item(
        val title: String?,
        val items: List<YTItem>,
    )
}
