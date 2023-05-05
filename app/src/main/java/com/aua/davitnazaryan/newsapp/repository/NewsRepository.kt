package com.aua.davitnazaryan.newsapp.repository

import com.aua.davitnazaryan.newsapp.api.RetrofitInstance
import com.aua.davitnazaryan.newsapp.model.NewsCategory

class NewsRepository {
    suspend fun getTopHeadlines(
        countryCode: String = "us",
        pageNumber: Int = 1,
        category: NewsCategory = NewsCategory.General
    ) = RetrofitInstance.api.getTopHeadlines(
        countryCode = countryCode,
        pageNumber = pageNumber,
        category = category.toString()
    )
}