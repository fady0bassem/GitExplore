package com.fadybassem.gitexplore.presentation.screens.main.listing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.usecase.github.GithubUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    val repositoriesList = mutableStateListOf<Repository>()

    val showApiError: MutableState<Pair<Boolean, String?>> = mutableStateOf(Pair(false, null))

    init {
        getPublicRepositories()
    }

    private fun getPublicRepositories(since: Int = 0) {
        viewModelScope.launch {
            githubUseCase.getPublicRepositories(since = since).onEach {
                apiStatus.value = it.apiStatus

                if (it.apiStatus == Status.SUCCESS) {
                    it.data?.let { repositories ->
                        if (since == 0) {
                            repositoriesList.clear()
                        }
                        repositoriesList.addAll(repositories)
                        showApiError.value = Pair(false, null)
                    }
                } else if (it.apiStatus == Status.FAILED || it.apiStatus == Status.ERROR) {
                    showApiError.value = Pair(true, it.message)
                }


            }.launchIn(viewModelScope)
        }
    }

    fun paginatePublicRepositories() {
        if (repositoriesList.isNotEmpty()) {
            val lastID = repositoriesList.last().id
            getPublicRepositories(since = lastID)
        }
    }
}