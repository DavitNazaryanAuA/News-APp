package com.aua.davitnazaryan.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.aua.davitnazaryan.newsapp.navigation.Navigation
import com.aua.davitnazaryan.newsapp.viewModel.NewsViewModel

class MainActivity : ComponentActivity() {

    private val newsViewModel by viewModels<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                Navigation(viewModel = newsViewModel)
            }
    }
}
