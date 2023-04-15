package com.aua.davitnazaryan.newsapp.repository

import com.aua.davitnazaryan.newsapp.api.RetrofitInstance

class NewsRepository {

    suspend fun getTopHeadlines(
        countryCode: String = "us",
        pageNumber: Int = 1,
    ) = RetrofitInstance.api.getTopHeadlines(countryCode = countryCode, pageNumber = pageNumber)
}