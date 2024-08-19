package com.fadybassem.gitexplore.presentation.screens.main.listing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.usecase.github.GithubUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val githubUseCase: GithubUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val user = helperUseCase.getUser()

    val searchQuery = mutableStateOf("")

    val showApiError: MutableState<Pair<Boolean, String?>> = mutableStateOf(Pair(false, null))

    private val _publicRepoFlow = MutableStateFlow<PagingData<Repository>>(PagingData.empty())
    val publicRepoFlow: StateFlow<PagingData<Repository>> = _publicRepoFlow

    private val _searchPageFlow = MutableStateFlow<PagingData<Repository>>(PagingData.empty())
    val searchPageFlow: StateFlow<PagingData<Repository>> = _searchPageFlow

    init {
        // Initialize the public repositories stream
        getPublicRepositories()
    }

    private fun getPublicRepositories() {
        viewModelScope.launch {
            githubUseCase.getPublicRepositoriesFlow().collectLatest { resource ->
                apiStatus.value = resource.apiStatus
                if (resource.apiStatus == Status.SUCCESS) {
                    resource.data?.cachedIn(viewModelScope)?.collectLatest { pagingData ->
                        _publicRepoFlow.value = pagingData
                    }
                } else if (resource.apiStatus == Status.ERROR || resource.apiStatus == Status.FAILED) {
                    showApiError.value = Pair(true, resource.message)
                    _publicRepoFlow.value = PagingData.empty()
                }
            }
        }
    }

    private fun searchRepositories(query: String) {
        viewModelScope.launch {
            githubUseCase.searchRepositoriesFlow(query).collectLatest { resource ->
                apiStatus.value = resource.apiStatus
                if (resource.apiStatus == Status.SUCCESS) {
                    resource.data?.cachedIn(viewModelScope)?.collectLatest { pagingData ->
                        _searchPageFlow.value = pagingData
                    }
                } else if (resource.apiStatus == Status.ERROR || resource.apiStatus == Status.FAILED) {
                    showApiError.value = Pair(true, resource.message)
                    _searchPageFlow.value = PagingData.empty()
                }
            }

        }
    }

    fun clearSearchQuery() {
        searchQuery.value = ""
        // Reset the search flow and load public repositories again
        _searchPageFlow.value = PagingData.empty()
        getPublicRepositories()
    }

    fun onSearchClick() {
        searchRepositories(query = searchQuery.value)
    }
}