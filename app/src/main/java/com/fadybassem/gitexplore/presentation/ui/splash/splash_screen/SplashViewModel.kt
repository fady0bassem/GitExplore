package com.fadybassem.gitexplore.presentation.ui.splash.splash_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.SPLASH_SCREEN_DELAY_DURATION
import com.fadybassem.gitexplore.usecase.authentication.AuthenticationUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.SplashNavigation
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val startNavigation: MutableState<SplashNavigation?> = mutableStateOf(value = null)

    val showErrorDialog = mutableStateOf(false)

    init {
        getFirebaseInstanceId()
    }

    private fun getFirebaseInstanceId() {
        viewModelScope.launch {
            authenticationUseCase.getFirebaseInstanceId().onEach {
                when (it.apiStatus) {
                    Status.SUCCESS -> {
                        showErrorDialog.value = false

                        delay(SPLASH_SCREEN_DELAY_DURATION)

                        if (helperUseCase.getPreferenceHelper().isLoggedIn()) {
                            startNavigation.value = SplashNavigation.Main
                        } else {
                            startNavigation.value = SplashNavigation.Login
                        }
                    }

                    Status.FAILED, Status.ERROR -> {
                        showErrorDialog.value = true
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }
}