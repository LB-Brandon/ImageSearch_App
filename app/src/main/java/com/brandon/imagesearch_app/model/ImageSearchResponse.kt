package com.brandon.imagesearch_app.model

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class ImageSearchResponse(
    @SerializedName("meta") val imageMeta: ImageMeta?,
    @SerializedName("documents") val imageDocuments: List<ImageDocument>?
)

data class ImageMeta(
    @SerializedName("total_count") val totalCount: Int?,
    @SerializedName("pageable_count") val pageableCount: Int?,
    @SerializedName("is_end") val isEnd: Boolean?
)

data class ImageDocument(
    @SerializedName("collection") val collection: String?,
    @SerializedName("thumbnail_url") val thumbnailUrl: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("display_sitename") val displaySiteName: String?,
    @SerializedName("doc_url") val docUrl: String?,
    @SerializedName("datetime") val datetime: String?
)

// 예외 처리와 함께 비동기적인 호출에 대한 prettyPrint 함수
fun Call<ImageSearchResponse>.prettyPrintAsync() {
    enqueue(object : Callback<ImageSearchResponse> {
        override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
            (if (response.isSuccessful) {
                response.body()?.prettyPrint() ?: "Response body is null"
            } else {
                "Unsuccessful response with code: ${response.code()}, message: ${response.message()}"
            })
        }

        override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
            ("Error: ${t.message}")
        }
    })
}

fun ImageSearchResponse.prettyPrint(): String {
    val metaString = "ImageMeta(totalCount=${
        imageMeta?.totalCount
    }, pageableCount=${imageMeta?.pageableCount}, isEnd=${imageMeta?.isEnd})"

    val documentsString = "ImageDocuments(" + imageDocuments?.joinToString(",\n") { document ->
        "  ImageDocument(collection=${document.collection}, thumbnailUrl=${document.thumbnailUrl}, imageUrl=${document.imageUrl}, " +
                "width=${document.width}, height=${document.height}, displaySitename=${document.displaySiteName}, " +
                "docUrl=${document.docUrl}, datetime=${document.datetime})"
    } + ")"

    return "ImageSearchResponse(\n  $metaString,\n  $documentsString\n)"
}
