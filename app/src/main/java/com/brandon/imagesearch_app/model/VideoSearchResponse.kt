package com.brandon.imagesearch_app.model

import com.google.gson.annotations.SerializedName

data class VideoSearchResponse(
    @SerializedName("meta") val videoMeta: VideoMeta?,
    @SerializedName("documents") val videoDocuments: List<VideoDocument>?
)

data class VideoMeta(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("pageable_count") val pageableCount: Int?,
    @SerializedName("is_end") val isEnd: Boolean?
)

data class VideoDocument(
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("datetime") val datetime: String?,
    @SerializedName("play_time") val playTime: Int?,
    @SerializedName("thumbnail") val thumbnailUrl: String?,
    @SerializedName("author") val author: String?
)
