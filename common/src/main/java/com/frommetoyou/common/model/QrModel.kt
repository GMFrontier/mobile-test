package com.frommetoyou.common.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


data class QrModel(
    val seed: String = "",
    val expiresAt: String = ""
) {
    companion object {
        fun getDefaultModel(expiresAt: String? = null) = QrModel(
            seed = "Hello world!",
            expiresAt = expiresAt ?: getCurrentTimePlus10Seconds()
        )

        private fun getCurrentTimePlus10Seconds(): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val currentTimeMillis = System.currentTimeMillis()
            val tenSecondsLater = currentTimeMillis + 10_000 // 10 segundos en ms
            val date = Date(tenSecondsLater)
            return dateFormat.format(date)
        }
    }
}
