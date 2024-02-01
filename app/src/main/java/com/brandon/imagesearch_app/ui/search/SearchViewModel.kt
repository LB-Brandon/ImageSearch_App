package com.brandon.imagesearch_app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.model.ImageDocument
import com.brandon.imagesearch_app.model.ImageSearchResponse
import com.brandon.imagesearch_app.model.VideoDocument
import com.brandon.imagesearch_app.network.NetworkClient
import com.brandon.imagesearch_app.util.TimeUtils.convertToFormattedTime
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber


class SearchViewModel : ViewModel() {

    private val _event: MutableLiveData<SearchEvent> = MutableLiveData()
    val event: LiveData<SearchEvent> get() = _event

    private val _uiState: MutableLiveData<SearchUiState> =
        MutableLiveData(SearchUiState.init())
    val uiState: LiveData<SearchUiState> get() = _uiState

    private val _queryUiState: MutableLiveData<SearchQueryUiState> = MutableLiveData(
        SearchQueryUiState.init()
    )
    val queryUiState: LiveData<SearchQueryUiState> get() = _queryUiState


    fun searchImages(query: String?) {
        query ?: return

        val apiService = NetworkClient.kakaoApiService
        val call = apiService.searchImages(query)

        call.enqueue(object : retrofit2.Callback<ImageSearchResponse> {
            override fun onResponse(
                call: Call<ImageSearchResponse>,
                response: Response<ImageSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val imageResponse = response.body()

                    // 데이터 저장
                    imageResponse?.let {
                        _uiState.value =
                            it.imageDocuments?.convertToListImageItem()?.let { searchResultList ->
                                uiState.value?.copy(
                                    searchResultList = searchResultList
                                )
                            }
                    } ?: run {
                        Timber.e("Response body is null")
                    }
                } else {
                    Timber.e("API request failed with code: ${response.code()}")
                    // Handle error
                }
            }

            override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
                Timber.e(t, "API request failed")
            }

        })
    }

    fun onClickItem(item: SearchResultItem?) {
        when (item) {
            is SearchResultItem.ImageItem -> _event.value = SearchEvent.OnClickItem(item)
            is SearchResultItem.VideoItem -> _event.value = SearchEvent.OnClickItem(item)
            is SearchResultItem.UnknownItem -> TODO()
            null -> TODO()
        }
    }

    fun doBookmark(item: SearchResultItem?) {
        val updatedList = uiState.value?.searchResultList?.map {
            when (it) {
                is SearchResultItem.ImageItem -> {
                    if (it.id == (item as? SearchResultItem.ImageItem)?.id) {

                        it.copy(isBookmarked = it.isBookmarked.not())
                    } else {
                        it
                    }
                }

                is SearchResultItem.VideoItem -> {
                    if (it.id == (item as? SearchResultItem.VideoItem)?.id) {
                        it.copy(isBookmarked = it.isBookmarked.not())
                    } else {
                        it
                    }
                }

                SearchResultItem.UnknownItem -> TODO()
            }
        }
        _uiState.value = uiState.value?.copy(searchResultList = updatedList.orEmpty())
    }

    fun onClickSearch() {
        _event.value = SearchEvent.OnClickSearch(_queryUiState.value?.query)
    }

    fun updateBookmarkedList() {
        _uiState.value?.searchResultList?.filter { item ->
            when (item) {
                is SearchResultItem.ImageItem -> item.isBookmarked
                is SearchResultItem.VideoItem -> item.isBookmarked
                SearchResultItem.UnknownItem -> TODO()
            }
        }?.let { bookmarkedItemList ->
            _uiState.value = uiState.value?.copy(bookmarkedList = bookmarkedItemList)
        }
    }

}

fun List<ImageDocument>.convertToListImageItem(): List<SearchResultItem.ImageItem> {

    return this.map {
        SearchResultItem.ImageItem(
            thumbnailUrl = it.thumbnailUrl,
            displaySiteName = it.displaySiteName,
            datetime = it.datetime?.convertToFormattedTime()
        )
    }
}

fun List<VideoDocument>.convertToListVideoItem(): List<SearchResultItem.VideoItem> {

    return this.map {
        SearchResultItem.VideoItem(
            title = it.title,
            thumbnailUrl = it.thumbnailUrl,
            datetime = it.datetime?.convertToFormattedTime()
        )
    }
}
