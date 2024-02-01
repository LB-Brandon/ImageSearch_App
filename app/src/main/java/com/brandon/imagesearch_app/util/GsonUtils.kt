package com.brandon.imagesearch_app.util

import com.brandon.imagesearch_app.data.BookmarkedItemModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtils {
    val gson = Gson()

    fun convertBookmarkedItemModelToString(searchResultItem: BookmarkedItemModel): String {
        return gson.toJson(searchResultItem)
    }

    fun convertStringToBookmarkItemModel(jsonString: String): BookmarkedItemModel {
        val typeToken = object : TypeToken<BookmarkedItemModel>() {}.type
        return gson.fromJson(jsonString, typeToken)
    }

}
