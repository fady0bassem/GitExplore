package com.fadybassem.gitexplore.repository.github

import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.models.github.RepositorySearch
import com.fadybassem.gitexplore.data_layer.remote.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun getPublicRepositories(
        since: Int = 0
    ): Flow<Resource<ArrayList<Repository>>>

    suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int = 30
    ): Flow<Resource<RepositorySearch>>
}