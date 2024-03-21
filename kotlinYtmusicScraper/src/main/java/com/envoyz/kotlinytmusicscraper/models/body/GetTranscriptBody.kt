package com.envoyz.kotlinytmusicscraper.models.body

import com.envoyz.kotlinytmusicscraper.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class GetTranscriptBody(
    val context: Context,
    val params: String,
)
