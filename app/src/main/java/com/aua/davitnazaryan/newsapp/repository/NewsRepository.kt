package com.aua.davitnazaryan.newsapp.repository

import com.aua.davitnazaryan.newsapp.api.RetrofitInstance
import com.aua.davitnazaryan.newsapp.model.NewsCategory

class NewsRepository {
    suspend fun getTopHeadlines(
        countryCode: String = "us",
        category: NewsCategory,
        search: String = "",
    ) = RetrofitInstance.api.getTopHeadlines(
        countryCode = countryCode,
        category = category.toString(),
        search = search
    )
}