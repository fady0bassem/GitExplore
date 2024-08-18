package com.fadybassem.gitexplore.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseApp(@ApplicationContext context: Context) =
        FirebaseApp.initializeApp(context)

    @Singleton
    @Provides
    fun provideFirebaseMessaging(@ApplicationContext context: Context) =
        FirebaseMessaging.getInstance()


    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}