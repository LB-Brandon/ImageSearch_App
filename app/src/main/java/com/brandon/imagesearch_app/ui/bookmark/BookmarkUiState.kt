package com.brandon.imagesearch_app.ui.bookmark

import com.brandon.imagesearch_app.data.SearchResultItem

data class BookmarkUiState(
    val bookmarkedList: List<SearchResultItem?>?
) {
    companion object {
        fun init() = BookmarkUiState(
            bookmarkedList = emptyList()
        )
    }
}
