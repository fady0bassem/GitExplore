package com.fadybassem.gitexplore.di

import android.content.Context
import android.content.SharedPreferences
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.SHARED_PREFERENCE
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.PreferenceUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Singleton
    @Provides
    fun sharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePreferenceUtils(sharedPreferences: SharedPreferences): PreferenceUtils =
        PreferenceUtils(sharedPreferences)

    @Singleton
    @Provides
    fun providePreferenceHelper(
        preferenceUtils: PreferenceUtils,
    ): PreferenceHelper =
        PreferenceHelper(preferenceUtils)
}