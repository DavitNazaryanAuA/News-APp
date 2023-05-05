package com.aua.davitnazaryan.newsapp.api

import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        countryCode: String,
        @Query("page")
        pageNumber: Int,
        @Query("category")
        category: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}