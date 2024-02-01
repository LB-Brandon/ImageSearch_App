package com.brandon.imagesearch_app.network

import com.brandon.imagesearch_app.model.VideoSearchResponse
import com.brandon.imagesearch_app.model.ImageSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface KakaoApiService {
    // 이미지 검색 API
    @GET("/v2/search/image")
    fun searchImages(
//        @Header("Authorization") authorization: String,
        @Query(value = "query", encoded = true) query: String,
        @Query("sort") sort: String? = "accuracy",
        @Query("page") page: Int? = 1,
        @Query("size") size: Int? = 10
    ): Call<ImageSearchResponse>

    // 동영상 검색 API
    @Headers("Authorization KakaoAK c2f850eb276516bb881564663fd727eb")
    @GET("/v2/search/vclip")
    fun searchVClip(
//        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String? = "accuracy",
        @Query("page") page: Int? = 1,
        @Query("size") size: Int? = 10
    ): Call<VideoSearchResponse>

    // page 1 부터 시작
    // 1 page 10 개 검색 아이템
}