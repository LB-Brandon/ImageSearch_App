package com.brandon.imagesearch_app.ui.search

enum class SearchListViewType {
    IMAGE,
    VIDEO
    ;

    companion object {
        fun from(ordinal: Int): SearchListViewType = SearchListViewType.values().find {
            it.ordinal == ordinal
        } ?: IMAGE
    }
}