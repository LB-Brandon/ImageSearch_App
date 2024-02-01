package com.brandon.imagesearch_app.data

import java.util.UUID

data class VideoModel(
    val thumbnailUrl: String?,
    val title: String?,
    val datetime: String?,
    val isBookmarked: Boolean? = false,
    val id: String? = UUID.randomUUID().toString(),
)
