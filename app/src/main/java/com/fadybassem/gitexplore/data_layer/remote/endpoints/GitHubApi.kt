package com.fadybassem.gitexplore.data_layer.remote.endpoints

import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.models.github.RepositorySearch
import com.fadybassem.gitexplore.data_layer.remote.responses.github.RepositoryResponse
import com.fadybassem.gitexplore.data_layer.remote.responses.github.RepositorySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("repositories")
    suspend fun getPublicRepositories(
        @Query("since") since: Int,
    ): List<RepositoryResponse>

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): RepositorySearchResponse
}