package com.fadybassem.gitexplore.usecase.github

import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.repository.github.GithubRepository
import com.fadybassem.gitexplore.utils.checkForNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class GithubUseCaseImpl(
    private val resourceProvider: ResourceProvider,
    private val networkManager: NetworkManager,
    private val repository: GithubRepository,
) : GithubUseCase {
    override suspend fun getPublicRepositories(
        since: Int,
    ): Flow<Resource<ArrayList<Repository>>> = flow {
        if (checkForNetwork(networkManager, resourceProvider)) return@flow

        repository.getPublicRepositories(since = since).onEach { emit(it) }
            .collect()
    }
}