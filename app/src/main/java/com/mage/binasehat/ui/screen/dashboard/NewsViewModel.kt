package com.mage.binasehat.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mage.binasehat.data.remote.api.NewsApiService
import com.mage.binasehat.data.remote.response.ArticlesItem
import com.mage.binasehat.data.util.UiState
import com.mage.binasehat.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _newsState = MutableStateFlow<UiState<List<ArticlesItem>>>(UiState.Loading)
    val newsState: StateFlow<UiState<List<ArticlesItem>>> = _newsState

    fun fetchNews() {
        viewModelScope.launch {
           newsRepository.getNews().collect { result ->
               _newsState.value = result.fold(
                   onSuccess = { articles ->
                       val nonNullArtilces = articles.filter { article ->
                           article.urlToImage != null && article.title != null && article.author != null
                       }
                       UiState.Success(nonNullArtilces)
                   },
                   onFailure = { UiState.Error(it.message.toString()) }
               )
           }
        }
    }
}