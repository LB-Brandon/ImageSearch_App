package com.brandon.imagesearch_app.util

import android.content.Context
import android.content.SharedPreferences
import com.brandon.imagesearch_app.data.BookmarkedItemModel
import com.brandon.imagesearch_app.data.SearchResultItem
import com.brandon.imagesearch_app.util.GsonUtils.gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesUtils {

    private const val PREFS_NAME = "MyPrefs"
    fun saveBookmarkedItems(context: Context, bookmarkedList: List<BookmarkedItemModel>?) {
        bookmarkedList?.let {
            val jsonStringList = it.map { bookmarkedItemModel ->
                GsonUtils.convertBookmarkedItemModelToString(bookmarkedItemModel)
            }

            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            with(sharedPreferences.edit()) {
                putString(Keys.BOOKMARKED_ITEMS_KEY, gson.toJson(bookmarkedList))
                apply()
            }
        }
    }

    fun getBookmarkedItems(context: Context): List<BookmarkedItemModel> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val jsonString = sharedPreferences.getString(Keys.BOOKMARKED_ITEMS_KEY, "")
        val type = object : TypeToken<List<BookmarkedItemModel>>() {}.type

        return gson.fromJson(jsonString, type) ?: emptyList()
    }
}
