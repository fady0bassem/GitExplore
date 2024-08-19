package com.fadybassem.gitexplore.repository.github

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.dto.github.GithubDTOMapper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.endpoints.GitHubApi
import com.fadybassem.gitexplore.data_layer.remote.network_exception.HandleApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GithubRepositoryImpl(
    private val service: GitHubApi,
    private val resourceProvider: ResourceProvider,
    private val handleApiError: HandleApiError,
    private val domainMapper: GithubDTOMapper,
) : GithubRepository {

    override fun getPublicRepositoriesPagingSource(): PagingSource<Int, Repository> {
        return object : PagingSource<Int, Repository>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
                return try {
                    val since = params.key ?: 0
                    val response = service.getPublicRepositories(since)
                    val data = domainMapper.mapPublicRepositories(response)
                    val nextKey = data.lastOrNull()?.id
                    LoadResult.Page(
                        data = data,
                        prevKey = null, // Only paging forward.
                        nextKey = nextKey
                    )
                } catch (exception: HttpException) {
                    val handleApiError = handleApiError.handleApiErrors(error = exception)
                    LoadResult.Error(Throwable(handleApiError.second))
                } catch (exception: Exception) {
                    LoadResult.Error(Throwable(resourceProvider.getString(R.string.generic_error)))
                }
            }

            override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.nextKey?.minus(1)
                }
            }
        }
    }

    override fun searchRepositoriesPagingSource(query: String): PagingSource<Int, Repository> {
        return object : PagingSource<Int, Repository>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
                return try {
                    val page = params.key ?: 1
                    val response = service.searchRepositories(query, page)
                    val data = domainMapper.mapSearchRepositories(response).items
                    LoadResult.Page(
                        data = data,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (data.isEmpty()) null else page + 1
                    )
                } catch (exception: HttpException) {
                    val handleApiError = handleApiError.handleApiErrors(error = exception)
                    LoadResult.Error(Throwable(handleApiError.second))
                } catch (exception: Exception) {
                    LoadResult.Error(Throwable(resourceProvider.getString(R.string.generic_error)))
                }
            }

            override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
                }
            }
        }
    }

    override suspend fun getRepository(id: Int): Flow<Resource<Repository>> =
        flow {
            try {
                emit(Resource.Loading())

                val response = service.getRepository(id = id)

                val data = domainMapper.mapRepositories(response)

                emit(Resource.Success(data = data))

            } catch (exception: HttpException) {
                val handleApiError = handleApiError.handleApiErrors(error = exception)
                exception.printStackTrace()
                emit(Resource.Failed(message = handleApiError.second, code = exception.code()))
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(Resource.Error(resourceProvider.getString(R.string.generic_error)))
            }
        }
}