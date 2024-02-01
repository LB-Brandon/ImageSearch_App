package com.brandon.imagesearch_app.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeUtils {
    private val dateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())

    private fun String.parseDateTime(): LocalDateTime {
        return LocalDateTime.parse(this, dateTimeFormatter)
    }

    private fun LocalDateTime.formatDateTime(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return this.format(formatter)
    }

    fun String.convertToFormattedTime(): String {
        return this.parseDateTime().formatDateTime()
    }
}