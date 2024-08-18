package com.fadybassem.gitexplore.di

import android.content.Context
import com.fadybassem.gitexplore.BuildConfig
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.network.Interceptor
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import com.fadybassem.gitexplore.data_layer.remote.network_exception.HandleApiError
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {

        val httpLogging = HttpLoggingInterceptor()
        httpLogging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(5, TimeUnit.MINUTES)
            .callTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .writeTimeout(5, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLogging)
        }
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideNetworkManager(@ApplicationContext context: Context): NetworkManager =
        NetworkManager(context = context)

    @Singleton
    @Provides
    fun provideHandleApiError(
        @ApplicationContext context: Context,
        preferenceHelper: PreferenceHelper,
        resourceProvider: ResourceProvider,
        networkManager: NetworkManager,
    ): HandleApiError = HandleApiError(
        context = context,
        preferenceHelper = preferenceHelper,
        resourceProvider = resourceProvider,
        networkManager = networkManager
    )

}