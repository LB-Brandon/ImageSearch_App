package com.brandon.imagesearch_app.data

import java.util.UUID

data class ImageModel(
    val thumbnailUrl: String?,
    val displaySiteName: String?,
    val datetime: String?,
    val isBookmarked: Boolean? = false,
    val id: String? = UUID.randomUUID().toString(),
)
