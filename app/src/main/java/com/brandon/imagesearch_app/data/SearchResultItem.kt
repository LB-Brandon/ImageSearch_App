package com.brandon.imagesearch_app.data

import com.brandon.imagesearch_app.ui.search.SearchListViewType
import java.util.UUID

sealed interface SearchResultItem {
    data class SearchItemList(
        val list: List<SearchResultItem>
    )

    data class ImageItem(
        val thumbnailUrl: String?,
        val displaySiteName: String?,
        val datetime: String?,
        var isBookmarked: Boolean = false,
        val id: String = UUID.randomUUID().toString(),
        val type: String? = SearchListViewType.IMAGE.name
    ) : SearchResultItem

    data class VideoItem(
        val thumbnailUrl: String?,
        val title: String?,
        val datetime: String?,
        var isBookmarked: Boolean = false,
        val id: String = UUID.randomUUID().toString(),
        val type: String? = SearchListViewType.VIDEO.name
    ) : SearchResultItem

    data object UnknownItem : SearchResultItem
}