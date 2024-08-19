package com.fadybassem.gitexplore.usecase.github

import androidx.paging.PagingData
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.remote.Resource
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {
    fun getPublicRepositoriesFlow(): Flow<Resource<Flow<PagingData<Repository>>>>
    fun searchRepositoriesFlow(query: String): Flow<Resource<Flow<PagingData<Repository>>>>
    suspend fun getRepository(id: Int): Flow<Resource<Repository>>
}