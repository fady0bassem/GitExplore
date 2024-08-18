package com.fadybassem.gitexplore.di

import com.fadybassem.gitexplore.data_layer.dto.authentication.AuthenticationDTOMapper
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.network_exception.firebase.HandleFireBaseError
import com.fadybassem.gitexplore.repository.authentication.AuthenticationRepository
import com.fadybassem.gitexplore.repository.authentication.AuthenticationRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthenticationRepository(
        resourceProvider: ResourceProvider,
        handleErrorResponse: HandleFireBaseError,
        firebaseMessaging: FirebaseMessaging,
        firebaseAuth: FirebaseAuth,
        domainMapper: AuthenticationDTOMapper,
    ): AuthenticationRepository = AuthenticationRepositoryImpl(
        resourceProvider = resourceProvider,
        handleErrorResponse = handleErrorResponse,
        firebaseMessaging = firebaseMessaging,
        firebaseAuth = firebaseAuth,
        domainMapper = domainMapper
    )
}