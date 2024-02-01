package com.brandon.imagesearch_app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.imagesearch_app.data.SearchResultItem

class MainViewModel : ViewModel() {

    private val _searchResultData: MutableLiveData<List<SearchResultItem>?> = MutableLiveData()
    val searchResultData: LiveData<List<SearchResultItem>?> get() = _searchResultData

    fun updateSearchResultData(data: List<SearchResultItem>?) {
        _searchResultData.value = data
    }
}
