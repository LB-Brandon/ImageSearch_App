package com.brandon.imagesearch_app.ui.search

import com.brandon.imagesearch_app.data.SearchResultItem

sealed interface SearchEvent {
    data class OnClickItem(
        val item: SearchResultItem?
    ) : SearchEvent
    data class OnClickSearch(
        val query: String?
    ) : SearchEvent
}
