package com.aua.davitnazaryan.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aua.davitnazaryan.newsapp.model.Article
import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.repository.NewsRepository
import com.aua.davitnazaryan.newsapp.ui.theme.NewsAppTheme
import com.aua.davitnazaryan.newsapp.util.Resource
import com.aua.davitnazaryan.newsapp.view.ArticleItem
import com.aua.davitnazaryan.newsapp.viewModel.NewsViewModel

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
                        MainPage(
                            resource = resource
                        )
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


@Composable
fun SearchBar(
) {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { newValue: String -> searchText = newValue },
        modifier = Modifier
            .background(Color.White),
        placeholder = { Text("Search") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        leadingIcon = {
            IconButton(
                onClick = { },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
        }
    )
}


@Composable
fun ArticleList(articlesResult: Resource<NewsResponse>) {

    if (articlesResult !is Resource.Success) return

    val articles = articlesResult.data?.articles ?: emptyList<Article>()
    LazyColumn {
        itemsIndexed(items = articles) { index, item ->
            ArticleItem(article = item)
        }
    }
}