package com.envoyz.kotlinytmusicscraper.models

import com.envoyz.kotlinytmusicscraper.models.subscriptionButton.SubscribeButtonRenderer
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionButton(
    val subscribeButtonRenderer: SubscribeButtonRenderer,
)