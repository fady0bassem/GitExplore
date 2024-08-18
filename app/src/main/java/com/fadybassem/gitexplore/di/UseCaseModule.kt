package com.fadybassem.gitexplore.di

import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import com.fadybassem.gitexplore.repository.authentication.AuthenticationRepository
import com.fadybassem.gitexplore.repository.github.GithubRepository
import com.fadybassem.gitexplore.usecase.authentication.AuthenticationUseCase
import com.fadybassem.gitexplore.usecase.authentication.AuthenticationUseCaseImpl
import com.fadybassem.gitexplore.usecase.github.GithubUseCase
import com.fadybassem.gitexplore.usecase.github.GithubUseCaseImpl
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideHelperUseCase(
        preferenceHelper: PreferenceHelper,
        resourceProvider: ResourceProvider,
    ): HelperUseCase = HelperUseCaseImpl(
        preferenceHelper = preferenceHelper,
        resourceProvider = resourceProvider,
    )

    @Singleton
    @Provides
    fun provideAuthenticationUseCase(
        repository: AuthenticationRepository,
        resourceProvider: ResourceProvider,
        preferenceHelper: PreferenceHelper,
        networkManager: NetworkManager,
    ): AuthenticationUseCase = AuthenticationUseCaseImpl(
        repository = repository,
        resourceProvider = resourceProvider,
        preferenceHelper = preferenceHelper,
        networkManager = networkManager,
    )

    @Singleton
    @Provides
    fun provideGithubUseCase(
        repository: GithubRepository,
        resourceProvider: ResourceProvider,
        networkManager: NetworkManager,
    ): GithubUseCase = GithubUseCaseImpl(
        repository = repository,
        resourceProvider = resourceProvider,
        networkManager = networkManager,
    )
}