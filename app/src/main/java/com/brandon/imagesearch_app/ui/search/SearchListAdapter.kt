package com.brandon.imagesearch_app.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.databinding.ImageItemBinding
import com.brandon.imagesearch_app.databinding.VideoItemBinding
import com.brandon.imagesearch_app.util.DiffUtil.diffUtil
import com.bumptech.glide.Glide
import timber.log.Timber

class SearchListAdapter(
    private val onClickItem: (SearchResultItem) -> Unit,
) :
    ListAdapter<SearchResultItem, SearchListAdapter.SearchViewHolder>(diffUtil) {

    abstract class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: SearchResultItem)
    }

    class ImageViewHolder(
        private val binding: ImageItemBinding,
        private val onClickItem: (SearchResultItem) -> Unit,
        ) : SearchViewHolder(binding.root) {
        override fun onBind(item: SearchResultItem) {
            if (item is SearchResultItem.ImageItem) {
                with(binding) {
                    // set Glide
                    item.thumbnailUrl?.let { url ->
                        Glide.with(itemView.context)
                            .load(url)
                            .into(ivMain)
                    }
                    tvType.text = "[Image]"
                    tvTitle.text = item.displaySiteName
                    tvTime.text = item.datetime
                    ivBookmark.isVisible = item.isBookmarked
                    container.setOnClickListener {
                        Timber.e("Item Clicked!")
                        onClickItem.invoke(item)
                    }
                }
            }
        }
    }

    class VideoViewHolder(
        private val binding: VideoItemBinding,
        onClickItem: (SearchResultItem) -> Unit
    ) : SearchViewHolder(binding.root) {
        override fun onBind(item: SearchResultItem) {
            if (item is SearchResultItem.VideoItem) {
                with(binding) {
                    // set Glide
                    item.thumbnailUrl?.let { url ->
                        Glide.with(itemView.context)
                            .load(url)
                            .into(ivMain)
                    }
                    tvType.text = "[Video]"
                    tvTitle.text = item.title
                    tvTime.text = item.datetime
                    ivBookmark.isVisible = item.isBookmarked
                    container.setOnClickListener {

                    }
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        // 아이템 타입에 따라 분기하여 해당하는 type number 를 enum class 의 ordinal 로 전달
        return getItem(position).getTypeOrdinal()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        // getItemViewType 으로 분리된 2개의 타입에 맞는 View Holder 에 대응하는 View Layout 을 바인딩
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SearchListViewType.IMAGE.ordinal -> ImageViewHolder(
                ImageItemBinding.inflate(
                    inflater,
                    parent,
                    false
                ),
                onClickItem
            )

            SearchListViewType.VIDEO.ordinal -> VideoViewHolder(
                VideoItemBinding.inflate(
                    inflater,
                    parent,
                    false
                ),
                onClickItem
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


}

fun SearchResultItem.getTypeOrdinal(): Int {
    return when (this) {
        is SearchResultItem.ImageItem -> SearchListViewType.IMAGE.ordinal
        is SearchResultItem.VideoItem -> SearchListViewType.VIDEO.ordinal
        SearchResultItem.UnknownItem -> TODO()
    }
}

