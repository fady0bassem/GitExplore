package com.fadybassem.gitexplore.presentation.screens.main.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.models.github.Repository
import com.fadybassem.gitexplore.usecase.github.GithubUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.REPOSITORY_ID
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val githubUseCase: GithubUseCase,
    private val resourceProvider: ResourceProvider,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val showApiError: MutableState<Pair<Boolean, String?>> = mutableStateOf(Pair(false, null))

    private val repositoryID = savedStateHandle.get<Int>(REPOSITORY_ID)

    val repository: MutableState<Repository?> = mutableStateOf(null)

    init {
        repositoryID?.let { getRepository(it) }
    }

    private fun getRepository(id: Int = 0) {
        viewModelScope.launch {
            githubUseCase.getRepository(id = id).onEach {
                apiStatus.value = it.apiStatus

                if (it.apiStatus == Status.SUCCESS) {
                    it.data?.let { data ->
                        showApiError.value = Pair(false, null)
                        repository.value = data
                    }
                } else if (it.apiStatus == Status.FAILED || it.apiStatus == Status.ERROR) {
                    showApiError.value = Pair(true, it.message)
                }

            }.launchIn(viewModelScope)
        }
    }
}