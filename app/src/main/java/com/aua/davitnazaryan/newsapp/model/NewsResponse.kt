package com.aua.davitnazaryan.newsapp.model

data class NewsResponse(
    val status: String,
    val totalResult: Int,
    val articles: MutableList<Article>
)