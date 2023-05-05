package com.aua.davitnazaryan.newsapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.aua.davitnazaryan.newsapp.model.Article
import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.util.Resource

@Composable
fun ArticleList(
    articlesResult: Resource<NewsResponse>?,
    articleOnClickHandler: (Article) -> Unit
) {
    //will add load and error screen later
    if (articlesResult !is Resource.Success) return

    val articles = articlesResult.data?.articles ?: emptyList()
    LazyColumn {
        itemsIndexed(items = articles) { _, item ->
            ArticleItem(article = item, onClickHandler = articleOnClickHandler)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleItem(
    article: Article,
    onClickHandler: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp,
        onClick = {onClickHandler(article)}
    ) {
        Row(
            Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {

            Image(
                painter = rememberImagePainter(
                    data = article.urlToImage,

                    builder = {
                        scale(Scale.FILL)
                        placeholder(coil.compose.base.R.drawable.notification_action_background)
                        transformations(CircleCropTransformation())

                    }
                ),
                contentDescription = article.url,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.2f)
            )


            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .weight(0.8f)
            ) {
                Text(
                    text = article.title ?: "Default Title",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = article.description ?: "Default Description",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .background(
                            Color.LightGray
                        )
                        .padding(4.dp)
                )
            }
        }

    }
}