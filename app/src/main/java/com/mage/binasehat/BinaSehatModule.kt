package com.mage.binasehat

import com.mage.binasehat.data.remote.ApiConstant
import com.mage.binasehat.data.remote.IoDispatcher
import com.mage.binasehat.data.remote.MainNews
import com.mage.binasehat.data.remote.api.NewsApiService
import com.mage.binasehat.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BinaSehatModule {

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApiService: NewsApiService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): NewsRepository {
        return NewsRepository(newsApiService, dispatcher)
    }


    @Provides
    @Singleton
    @MainNews
    fun newsApi(): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
        val client = clientBuilder.build()

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiConstant.NEWS_BASE_URL).build()
    }

    // create instance of NewsApiService
    @Provides
    @Singleton
    fun newsApiService(@MainNews retrofit: Retrofit): NewsApiService = retrofit.create(NewsApiService::class.java)

}