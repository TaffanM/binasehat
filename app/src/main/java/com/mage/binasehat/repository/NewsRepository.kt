package com.mage.binasehat.repository

import com.mage.binasehat.data.remote.api.NewsApiService
import com.mage.binasehat.data.remote.response.ArticlesItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApiService: NewsApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getNews(): Flow<Result<List<ArticlesItem>>> = flow {
        try {
            val response = newsApiService.getArticles()
            emit(Result.success(response.articles ?: emptyList()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(dispatcher)

}