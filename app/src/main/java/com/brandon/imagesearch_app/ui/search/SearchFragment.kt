package com.brandon.imagesearch_app.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.databinding.SearchFragmentBinding
import com.brandon.imagesearch_app.ui.viewmodel.SharedViewModel
import com.brandon.imagesearch_app.util.Keys.LAST_SEARCH_QUERY_KEY
import com.brandon.imagesearch_app.util.Keys.PREFS_NAME


class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    private val listAdapter: SearchListAdapter by lazy {
        SearchListAdapter(
            onClickItem = {
                viewModel.onClickItem(it)
            }
        )

    }

    companion object {
        fun newInstance() = SearchFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initViewModel()
        // 마지막 검색어 복원
        restoreLastSearch()
    }

    private fun restoreLastSearch() {
        val lastQuery = getLastQuery()

        binding.etSearchBar.setText(lastQuery)
        // TODO: 복원된 검색어를 사용하여 검색 수행
        if (lastQuery != null) {
            viewModel.searchImages(lastQuery)
        }
    }

    private fun getLastQuery(): String? {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LAST_SEARCH_QUERY_KEY, "")
    }

    private fun saveLastSearch(query: String?) {
        query ?: return
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(LAST_SEARCH_QUERY_KEY, query).apply()
    }

    private fun saveBookmarkedItems(bookmarkedList: List<SearchResultItem>?) {
        sharedViewModel.updateSharedData(bookmarkedList)
    }


    private fun initListener() = with(binding) {
        tvSearch.setOnClickListener {
            hideKeyboard()
            viewModel.onClickSearch()
        }
        etSearchBar.addTextChangedListener {
            viewModel.queryUiState.value?.query = it.toString()
        }
    }

    private fun SearchFragmentBinding.hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(tvSearch.windowToken, 0)
    }

    private fun initViewModel() = with(viewModel) {
        event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is SearchEvent.OnClickItem -> {
                    doBookmark(event.item)
                    updateBookmarkedList()
                }

                is SearchEvent.OnClickSearch -> {
                    saveLastSearch(event.query)
                    searchImages(event.query)
                }
            }
        }
        uiState.observe(viewLifecycleOwner) { state ->
            saveBookmarkedItems(state.bookmarkedList)
            listAdapter.submitList(state.searchResultList)
        }
    }


    private fun initView() = with(binding) {
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    fun upToTop() = with(binding) {
        recyclerView.smoothScrollToPosition(0)
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}