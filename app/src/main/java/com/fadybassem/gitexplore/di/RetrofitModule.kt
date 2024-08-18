package com.fadybassem.gitexplore.di

import com.fadybassem.gitexplore.data_layer.local.AppConfiguration
import com.fadybassem.gitexplore.data_layer.remote.endpoints.GitHubApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    val client = OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()).build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit.Builder {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().baseUrl(AppConfiguration.API_URL).client(httpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideGitHubApi(retrofit: Retrofit.Builder): GitHubApi =
        retrofit.build().create(GitHubApi::class.java)
}