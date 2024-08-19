package com.fadybassem.gitexplore.usecase.github

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
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

    override fun getPublicRepositoriesFlow(): Flow<Resource<Flow<PagingData<Repository>>>> = flow {
        if (checkForNetwork(networkManager, resourceProvider)) {
            return@flow
        }
        emit(Resource.Loading())

        val pagingFlow = Pager(config = PagingConfig(
            pageSize = 30, // Number of items per page
            initialLoadSize = 30, // Number of items to load initially
            enablePlaceholders = false
        ), pagingSourceFactory = {
            repository.getPublicRepositoriesPagingSource()
        }).flow

        try {
            // Emit the paging flow wrapped in success
            emit(Resource.Success(data = pagingFlow))
        } catch (exception: Exception) {
            // Emit failed status in case of an error
            emit(Resource.Failed(message = exception.message))
        }
    }

    override fun searchRepositoriesFlow(query: String): Flow<Resource<Flow<PagingData<Repository>>>> =
        flow {
            if (checkForNetwork(networkManager, resourceProvider)) {
                return@flow
            }

            emit(Resource.Loading())

            val pagingFlow = Pager(config = PagingConfig(
                pageSize = 30, // Number of items per page
                initialLoadSize = 30, // Number of items to load initially
                enablePlaceholders = false
            ), pagingSourceFactory = {
                repository.searchRepositoriesPagingSource(query)
            }).flow

            try {
                // Emit the paging flow wrapped in success
                emit(Resource.Success(data = pagingFlow))
            } catch (exception: Exception) {
                // Emit failed status in case of an error
                emit(Resource.Failed(message = exception.message))
            }
        }

    override suspend fun getRepository(id: Int): Flow<Resource<Repository>> = flow {
        if (checkForNetwork(networkManager, resourceProvider)) return@flow

        repository.getRepository(id = id).onEach { emit(it) }.collect()
    }
}