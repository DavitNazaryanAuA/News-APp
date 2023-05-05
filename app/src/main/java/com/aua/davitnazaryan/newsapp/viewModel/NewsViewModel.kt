package com.aua.davitnazaryan.newsapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aua.davitnazaryan.newsapp.model.NewsCategory
import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.repository.NewsRepository
import com.aua.davitnazaryan.newsapp.util.Resource
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    val topHeadlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    private var topHeadlinesPage = 1
    private var currentTopHeadlines: NewsResponse? = null
    private var newsRepository = NewsRepository()

    fun updateTopHeadlines(category: NewsCategory = NewsCategory.General) = viewModelScope.launch{
        topHeadlines.postValue(Resource.Loading())
        topHeadlines.value = updateCurrentTopHeadlines(category = category)
    }

    private suspend fun updateCurrentTopHeadlines(category: NewsCategory): Resource<NewsResponse> {
        val topHeadlinesResponse = try {
            newsRepository.getTopHeadlines(category = category)
        } catch (e: Error) {
            return Resource.Error("Error Getting News!")
        }

        if (!topHeadlinesResponse.isSuccessful) return Resource.Error("Something Went Wrong!")

        topHeadlinesResponse.body()?.let {
            topHeadlinesPage++
            it.articles.addAll(currentTopHeadlines?.articles ?: emptyList())
            it.articles.reverse()
            currentTopHeadlines = it
        }

        return if (currentTopHeadlines != null) Resource.Success(currentTopHeadlines!!) else Resource.Error("Error, Nothing to show!")
    }
}