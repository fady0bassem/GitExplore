package com.fadybassem.gitexplore.repository.github

import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.dto.github.GithubDTOMapper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.data_layer.models.github.RepositorySearch
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

    override suspend fun getPublicRepositories(since: Int): Flow<Resource<ArrayList<Repository>>> =
        flow {
            try {
                emit(Resource.Loading())

                val response = service.getPublicRepositories(since = since)

                val data = domainMapper.mapPublicRepositories(response)

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

    override suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int,
    ): Flow<Resource<RepositorySearch>> = flow {
        try {
            emit(Resource.Loading())

            val response = service.searchRepositories(query = query, page = page, perPage = perPage)

            val data = domainMapper.mapSearchRepositories(response)

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