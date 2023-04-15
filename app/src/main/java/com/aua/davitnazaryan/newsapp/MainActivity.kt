package com.aua.davitnazaryan.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.repository.NewsRepository
import com.aua.davitnazaryan.newsapp.ui.theme.NewsAppTheme
import com.aua.davitnazaryan.newsapp.util.Resource
import com.aua.davitnazaryan.newsapp.viewModel.NewsViewModel
import com.aua.davitnazaryan.newsapp.views.ArticleList
import com.aua.davitnazaryan.newsapp.views.SearchBar

class MainActivity : ComponentActivity() {

    val articleViewModel = NewsViewModel(NewsRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleViewModel.updateTopHeadlines()

        articleViewModel.topHeadlines.observe(this) { resource ->
            setContent {
                NewsAppTheme {
                    Surface(
                        color = MaterialTheme.colors.background,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        MainPage(resource = resource)
                    }
                }
            }
        }

    }
}

@Composable
fun MainPage(
    resource: Resource<NewsResponse>
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color(0xFF0645AA))
        ) {
            Column() {
                Text(
                    text = "News ",
                    style = MaterialTheme.typography.h2,
                    color = Color.White,
                    modifier = Modifier.padding(15.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    SearchBar()
                }
            }
        }
        ArticleList(articlesResult = resource)
    }
}
