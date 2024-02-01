package com.brandon.imagesearch_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.imagesearch_app.data.SearchResultItem

class SharedViewModel : ViewModel() {
    private val _sharedData = MutableLiveData<List<SearchResultItem>?>()
    val sharedData: LiveData<List<SearchResultItem>?> get() = _sharedData

    fun updateSharedData(data: List<SearchResultItem>?) {
        data?: return
        _sharedData.value = data
    }


}