package com.brandon.imagesearch_app.network

import com.brandon.imagesearch_app.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object NetworkClient {
    private const val KAKAO_API_BASE_URL = "https://dapi.kakao.com/"
    private const val API_KEY = "KakaoAK c2f850eb276516bb881564663fd727eb"
    // 로깅 Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor { message -> // Timber를 사용하여 로그를 출력
        Timber.tag("OkHttp").e(message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient 설정
    private val okHttpClient = OkHttpClient.Builder().addInterceptor {
        val request = it.request().newBuilder().addHeader("Authorization", API_KEY).build()
        it.proceed(request)
    }.addInterceptor(loggingInterceptor).build()

    // Retrofit 설정
    private val retrofitClient = Retrofit.Builder().baseUrl(KAKAO_API_BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    // KakaoApiService 인스턴스 생성
    val kakaoApiService: KakaoApiService = retrofitClient.create(KakaoApiService::class.java)
}


//class AuthInterceptor(private val token: String) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request().newBuilder()
//            .addHeader("Authorization", token)
//            .build()
//        return chain.proceed(request)
//    }
//}