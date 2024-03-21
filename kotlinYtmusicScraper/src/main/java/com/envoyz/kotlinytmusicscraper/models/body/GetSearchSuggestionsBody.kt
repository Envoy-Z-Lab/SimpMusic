package com.envoyz.kotlinytmusicscraper.models.body

import com.envoyz.kotlinytmusicscraper.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchSuggestionsBody(
    val context: Context,
    val input: String,
)
