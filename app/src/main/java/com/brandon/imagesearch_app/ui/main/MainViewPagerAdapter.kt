package com.brandon.imagesearch_app.ui.main

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brandon.imagesearch_app.R
import com.brandon.imagesearch_app.ui.bookmark.BookmarkFragment
import com.brandon.imagesearch_app.ui.search.SearchFragment


class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragments: List<MainTabHolder> = listOf(
        MainTabHolder(
            fragment = SearchFragment.newInstance(), title = R.string.Main_tab_title_search
        ), MainTabHolder(
            fragment = BookmarkFragment.newInstance(), title = R.string.Main_tab_title_bookmark
        )
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position].fragment

    fun getFragment(position: Int) = fragments[position].fragment

    fun getTitle(position: Int) = fragments[position].title

    fun getSearchFragment(): SearchFragment? = fragments.find {
        it.fragment is SearchFragment
    }?.fragment as SearchFragment?

    fun getBookmarkFragment(): BookmarkFragment? = fragments.find {
        it.fragment is BookmarkFragment
    }?.fragment as BookmarkFragment?
}