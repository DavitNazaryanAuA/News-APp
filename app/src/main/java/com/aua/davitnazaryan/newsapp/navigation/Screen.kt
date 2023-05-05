package com.aua.davitnazaryan.newsapp.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen(route = "main_screen")
    object DetailedArticleScreen: Screen(route = "article_detailed/{article}")
}
