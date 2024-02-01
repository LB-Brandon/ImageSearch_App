package com.brandon.imagesearch_app.util

import androidx.recyclerview.widget.DiffUtil
import com.brandon.imagesearch_app.data.SearchResultItem
import timber.log.Timber

object DiffUtil {
    val diffUtil = object : DiffUtil.ItemCallback<SearchResultItem>() {
        override fun areItemsTheSame(
            oldItem: SearchResultItem,
            newItem: SearchResultItem
        ): Boolean {
            return when {
                oldItem is SearchResultItem.ImageItem && newItem is SearchResultItem.ImageItem ->
                    oldItem == newItem

                oldItem is SearchResultItem.VideoItem && newItem is SearchResultItem.VideoItem ->
                    oldItem == newItem

                else -> run {
                    Timber.e("Can't match the type")
                    false
                }
            }
        }

        override fun areContentsTheSame(
            oldItem: SearchResultItem,
            newItem: SearchResultItem
        ): Boolean {
            return true
        }
    }
}