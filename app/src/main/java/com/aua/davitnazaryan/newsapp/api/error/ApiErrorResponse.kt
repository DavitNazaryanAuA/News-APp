package com.aua.davitnazaryan.newsapp.api.error

data class ApiErrorResponse(
    val protocol: String,
    val code: Int,
    val message: String,
    val url: String
)
