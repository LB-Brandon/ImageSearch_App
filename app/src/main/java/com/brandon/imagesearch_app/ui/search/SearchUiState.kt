package com.brandon.imagesearch_app.ui.search

import com.brandon.imagesearch_app.data.SearchResultItem

data class SearchUiState(
    val searchResultList: List<SearchResultItem>?,
    val bookmarkedList: List<SearchResultItem>?,
){
    companion object {
        fun init() = SearchUiState(
            searchResultList = emptyList(),
            bookmarkedList = emptyList()
        )
    }
}

data class SearchQueryUiState(
    var query: String?
){
    companion object {
        fun init() = SearchQueryUiState(
            query = ""
        )
    }
}


