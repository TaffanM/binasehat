package com.mage.binasehat

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mage.binasehat.background.tracking.service.DefaultBackgroundTrackingManager
import com.mage.binasehat.data.local.db.RunTrackDB
import com.mage.binasehat.data.local.db.RunTrackDB.Companion.RUN_TRACK_DB_NAME
import com.mage.binasehat.data.local.db.dao.RunDao
import com.mage.binasehat.data.remote.ApiConstant
import com.mage.binasehat.data.remote.IoDispatcher
import com.mage.binasehat.data.remote.MainNews
import com.mage.binasehat.data.remote.api.NewsApiService
import com.mage.binasehat.data.tracking.location.DefaultLocationTrackingManager
import com.mage.binasehat.data.tracking.timer.DefaultTimeTracker
import com.mage.binasehat.domain.tracking.background.BackgroundTrackingManager
import com.mage.binasehat.domain.tracking.location.LocationTrackingManager
import com.mage.binasehat.domain.tracking.timer.TimeTracker
import com.mage.binasehat.repository.NewsRepository
import com.mage.binasehat.repository.RunRepository
import com.mage.binasehat.ui.util.LocationUtility
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
abstract class BinaSehatModule {

    companion object {

        @Singleton
        @Provides
        fun provideFusedLocationProviderClient(
            @ApplicationContext context: Context
        ) = LocationServices
            .getFusedLocationProviderClient(context)

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
        fun provideRunRepository(
            runDao: RunDao
        ): RunRepository {
            return RunRepository(runDao)
        }

        @Singleton
        @Provides
        fun provideLocationTrackingManager(
            @ApplicationContext context: Context,
            fusedLocationProviderClient: FusedLocationProviderClient,
        ): LocationTrackingManager {
            return DefaultLocationTrackingManager(
                fusedLocationProviderClient = fusedLocationProviderClient,
                context = context,
                locationRequest = LocationUtility.locationRequestBuilder.build()
            )
        }

        @Provides
        @Singleton
        fun provideRunningDB(
            @ApplicationContext context: Context
        ): RunTrackDB = Room.databaseBuilder(
            context,
            RunTrackDB::class.java,
            RUN_TRACK_DB_NAME
        ).build()

        @Singleton
        @Provides
        fun provideRunDao(db: RunTrackDB) = db.getRunDao()



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



    @Binds
    @Singleton
    abstract fun provideBackgroundTrackingManager(
        trackingServiceManager: DefaultBackgroundTrackingManager
    ): BackgroundTrackingManager

    @Binds
    @Singleton
    abstract fun provideTimeTracker(
        timeTracker: DefaultTimeTracker
    ): TimeTracker

}