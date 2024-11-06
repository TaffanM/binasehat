package com.mage.binasehat.data.remote.api

import com.mage.binasehat.data.remote.ApiConstant
import com.mage.binasehat.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getArticles(
        @Query("q") query: String = "healthy+nutrition",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("language") language: String = "en",
        @Query("apiKey") api: String = ApiConstant.NEWS_API_KEY
    ): NewsResponse

}