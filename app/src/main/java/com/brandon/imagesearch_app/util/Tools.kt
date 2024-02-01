package com.brandon.imagesearch_app.util

import com.brandon.imagesearch_app.data.BookmarkedItemModel
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.ui.search.SearchListViewType

object Tools {

    fun convertToBookmarkedItemModel(item: SearchResultItem): BookmarkedItemModel {
        return when (item) {
            is SearchResultItem.ImageItem -> {
                BookmarkedItemModel(
                    thumbnailUrl = item.thumbnailUrl,
                    title = item.displaySiteName,
                    datetime = item.datetime,
                    id = item.id,
                    type = item.type
                )
            }

            is SearchResultItem.VideoItem -> {
                // You might need to handle video-specific properties here
                BookmarkedItemModel(
                    thumbnailUrl = null, // Set the appropriate value for video thumbnail
                    title = item.title,
                    datetime = item.datetime,
                    id = item.id,
                    type = item.type
                )
            }

            SearchResultItem.UnknownItem -> TODO()
        }
    }

    fun convertToSearchResultItem(bookmarkedItemModelList: List<BookmarkedItemModel>): List<SearchResultItem>? {
        val SearchResultItemList = bookmarkedItemModelList.map { bookmarkedItemModel ->
            when (bookmarkedItemModel.type) {
                SearchListViewType.IMAGE.name -> {
                    bookmarkedItemModel.id?.let {
                        SearchResultItem.ImageItem(
                            thumbnailUrl = bookmarkedItemModel.thumbnailUrl,
                            displaySiteName = bookmarkedItemModel.title,
                            datetime = bookmarkedItemModel.datetime,
                            id = it
                        )
                    }
                }

                SearchListViewType.VIDEO.name -> {
                    bookmarkedItemModel.id?.let {
                        SearchResultItem.VideoItem(
                            thumbnailUrl = bookmarkedItemModel.thumbnailUrl,
                            title = bookmarkedItemModel.title,
                            datetime = bookmarkedItemModel.datetime,
                            id = it
                        )
                    }
                }

                else -> SearchResultItem.UnknownItem
            }

        }
        return SearchResultItemList.mapNotNull { it }
    }


}