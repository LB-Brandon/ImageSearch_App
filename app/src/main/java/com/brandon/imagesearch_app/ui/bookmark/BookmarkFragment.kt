package com.brandon.imagesearch_app.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.databinding.BookmarkFragmentBinding
import com.brandon.imagesearch_app.ui.search.SearchListAdapter
import com.brandon.imagesearch_app.ui.viewmodel.SharedViewModel
import com.brandon.imagesearch_app.util.SharedPreferencesUtils
import com.brandon.imagesearch_app.util.Tools.convertToSearchResultItem
import timber.log.Timber

class BookmarkFragment : Fragment() {

    private var _binding: BookmarkFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookmarkViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val listAdapter: SearchListAdapter by lazy {
        SearchListAdapter(
            onClickItem = {
                viewModel.onClickItem(it)
            }
        )

    }

    companion object {
        fun newInstance() = BookmarkFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = BookmarkFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun restoreBookmarkedItems() {
        val restoredBookmarkedItemList = SharedPreferencesUtils.getBookmarkedItems(requireContext())
        Timber.d("Restored Model Data: $restoredBookmarkedItemList")
        val searchResultItemList = convertToSearchResultItem(restoredBookmarkedItemList)
        Timber.d("Restored Data: $searchResultItemList")
        viewModel.updateUiState(searchResultItemList)
    }


    private fun initView() = with(binding) {
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) {
            Timber.e("UI Data Changed!")
            listAdapter.submitList(it.bookmarkedList.orEmpty())
        }
        sharedViewModel.sharedData.observe(viewLifecycleOwner) { searchResultItemList ->
            Timber.e("Shared Data Changed!")
            updateUiState(searchResultItemList)
        }
        bookmarkedItemList.observe(viewLifecycleOwner) { bookmarkedItemList ->
            Timber.e("BookmarkedList Data Changed!")
            SharedPreferencesUtils.saveBookmarkedItems(requireContext(), bookmarkedItemList)
        }
    }


    fun onClickItem(item: SearchResultItem?) {
//        when (item) {
//            is SearchResultItem.ImageItem -> _event.value = SearchEvent.OnClickItem(item)
//            is SearchResultItem.VideoItem -> _event.value = SearchEvent.OnClickItem(item)
//            null -> TODO()
//        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}