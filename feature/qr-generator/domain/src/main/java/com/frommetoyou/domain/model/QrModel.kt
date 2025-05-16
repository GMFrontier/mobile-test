package com.frommetoyou.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QrModel(
    val seed: String = "",
    val expiresAt: String = ""
) {
    companion object {
        fun getDefaultModel() = QrModel(
            seed = "Hello world!",
            expiresAt = "2025-11-12T13:10:42.24Z"
        )
    }
}
