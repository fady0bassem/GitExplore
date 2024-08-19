package com.fadybassem.gitexplore.presentation.screens.account_settings.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.usecase.authentication.AuthenticationUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel  @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val user = helperUseCase.getUser()

    val navigateToMainScreen: MutableState<Boolean?> = mutableStateOf(null)
    val showErrorDialog: MutableState<Pair<Boolean?, String?>> = mutableStateOf(Pair(null, null))

    fun logout() {
        viewModelScope.launch {
            authenticationUseCase.logout().onEach { user ->
                apiStatus.value = user.apiStatus
                if (user.apiStatus == Status.SUCCESS) {
                    navigateToMainScreen.value = true
                } else if (apiStatus.value == Status.FAILED) {
                    showErrorDialog.value = Pair(true, user.message)
                }
            }.launchIn(viewModelScope)
        }
    }
}