package com.aua.davitnazaryan.newsapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aua.davitnazaryan.newsapp.model.NewsResponse
import com.aua.davitnazaryan.newsapp.repository.NewsRepository
import com.aua.davitnazaryan.newsapp.util.Resource
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    var currentTopHeadlines: NewsResponse? = null
    val topHeadlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var topHeadlinesPage = 1


    fun updateTopHeadlines() = viewModelScope.launch{
        topHeadlines.postValue(Resource.Loading())
        val displayData = handleNewTopHeadlines()
        topHeadlines.value = displayData
    }

    private suspend fun handleNewTopHeadlines(): Resource<NewsResponse> {
        val topHeadlinesResponse = try {
            newsRepository.getTopHeadlines()
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