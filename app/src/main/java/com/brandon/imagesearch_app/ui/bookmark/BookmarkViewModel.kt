package com.brandon.imagesearch_app.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.imagesearch_app.data.BookmarkedItemModel
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.util.Tools.convertToBookmarkedItemModel

class BookmarkViewModel : ViewModel() {

    private val _event: MutableLiveData<BookmarkEvent> = MutableLiveData()
    val event: LiveData<BookmarkEvent> get() = _event

    private val _uiState: MutableLiveData<BookmarkUiState> = MutableLiveData(BookmarkUiState.init())
    val uiState: LiveData<BookmarkUiState> get() = _uiState

    private val _bookmarkedItemList: MutableLiveData<List<BookmarkedItemModel>> = MutableLiveData()
    val bookmarkedItemList: LiveData<List<BookmarkedItemModel>> get() = _bookmarkedItemList

    fun updateUiState(items: List<SearchResultItem>?) {
        items?: return
        updateBookmarkedItemList(items)
        val updatedItems = items.map { item ->
            when(item){
                is SearchResultItem.ImageItem -> item.copy(isBookmarked = false)
                is SearchResultItem.VideoItem -> item.copy(isBookmarked = false)
                SearchResultItem.UnknownItem -> TODO()
            }
        }
        _uiState.value = uiState.value?.copy(bookmarkedList = updatedItems)
    }

    private fun updateBookmarkedItemList(items: List<SearchResultItem>?){
        items?: return
        val bookmarkedItems = items.map {item->
            convertToBookmarkedItemModel(item)
        }
        _bookmarkedItemList.value = bookmarkedItems
    }


    fun onClickItem(it: SearchResultItem) {
        TODO("Not yet implemented")
    }


}