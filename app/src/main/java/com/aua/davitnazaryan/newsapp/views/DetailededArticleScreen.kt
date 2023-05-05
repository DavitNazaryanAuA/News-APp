package com.aua.davitnazaryan.newsapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.aua.davitnazaryan.newsapp.model.Article

@Composable
fun DetailedArticleScreen(navController: NavController, article: Article) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(backgroundColor = Color.Transparent, elevation = 0.dp) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Read more", fontWeight = FontWeight.Bold)
            }
        }

        Text(text = article.title ?: "Default Title", style = MaterialTheme.typography.h4)

        AsyncImage(
            model = article.urlToImage,
            contentDescription = "article image",
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = article.description?: "Default Description",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(vertical = 20.dp)
        )


        Text(
            text = "Published at: ${article.publishedAt ?: "N/A"}",
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            text = "Author: ${article.author ?: "N/A"}",
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Text(
            text = "Source: ${article.source?.name ?: "N/A"}",
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Divider(Modifier.fillMaxWidth())
        Text(
            text = article.content ?: "Default Content",
            modifier = Modifier.padding(bottom = 20.dp)
        )

        val uriHandler = LocalUriHandler.current
        Text(
            text = "Read More",
            modifier = Modifier.clickable(
                onClick = {
                    uriHandler.openUri(article.url ?: "")
                }
            ),
            color = Color.Blue,
            textDecoration = TextDecoration.Underline
        )
    }
}

