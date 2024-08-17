package com.fadybassem.gitexplore.di

import android.content.Context
import com.fadybassem.gitexplore.app.BaseApplication
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): BaseApplication =
        context as BaseApplication

    @Singleton
    @Provides
    fun provideLocalManager(
        @ApplicationContext context: Context,
        preferenceHelper: PreferenceHelper
    ): ResourceProvider =
        ResourceProvider(context = context, preferenceHelper = preferenceHelper)
}