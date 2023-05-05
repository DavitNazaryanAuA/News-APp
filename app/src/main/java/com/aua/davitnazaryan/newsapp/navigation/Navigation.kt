package com.aua.davitnazaryan.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aua.davitnazaryan.newsapp.GsonSerializer
import com.aua.davitnazaryan.newsapp.model.Article
import com.aua.davitnazaryan.newsapp.viewModel.NewsViewModel
import com.aua.davitnazaryan.newsapp.views.DetailedArticleScreen
import com.aua.davitnazaryan.newsapp.views.MainScreen

@Composable
fun Navigation(
    viewModel: NewsViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable( route = Screen.MainScreen.route) {
            MainScreen(viewModel = viewModel, navController = navController )
        }
        
        composable(
            route = Screen.DetailedArticleScreen.route,
            arguments = listOf(
                navArgument(name = "article") {
                    type = NavType.StringType
                }
            )
        ) {entry ->
            val articleJson = entry.arguments?.getString("article")
            val article = GsonSerializer.fromJson(articleJson, Article::class.java)
            DetailedArticleScreen(navController = navController, article = article)
        }
    }
}