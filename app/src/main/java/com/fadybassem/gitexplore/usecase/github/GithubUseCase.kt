package com.fadybassem.gitexplore.usecase.github

import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.models.github.RepositorySearch
import com.fadybassem.gitexplore.data_layer.remote.Resource
import kotlinx.coroutines.flow.Flow

interface GithubUseCase {

    suspend fun getPublicRepositories(
        since: Int = 30
    ): Flow<Resource<ArrayList<Repository>>>

    suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int = 30
    ): Flow<Resource<RepositorySearch>>
}