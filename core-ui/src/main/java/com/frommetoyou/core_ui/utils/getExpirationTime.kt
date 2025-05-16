package com.frommetoyou.core_ui.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun parseExpirationTime(expiresAt: String): Long {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return dateFormat.parse(expiresAt)?.time ?: 0L
}