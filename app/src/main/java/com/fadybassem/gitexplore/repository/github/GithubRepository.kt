package com.fadybassem.gitexplore.repository.github

import androidx.paging.PagingSource
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.remote.Resource
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getPublicRepositoriesPagingSource(): PagingSource<Int, Repository>
    fun searchRepositoriesPagingSource(query: String): PagingSource<Int, Repository>
    suspend fun getRepository(id: Int): Flow<Resource<Repository>>
}