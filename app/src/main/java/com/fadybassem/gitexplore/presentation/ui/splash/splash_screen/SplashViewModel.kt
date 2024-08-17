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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    authenticationUseCase: AuthenticationUseCase,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val startNavigation: MutableState<SplashNavigation?> = mutableStateOf(value = null)

    init {
        authenticationUseCase.validateFirebaseInstanceId {}

        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY_DURATION)
            when {
                helperUseCase.getPreferenceHelper().isLoggedIn() -> {
                    when (helperUseCase.getUser()?.firstLogin) {
                        true -> startNavigation.value = SplashNavigation.CompleteProfile
                        else -> startNavigation.value = SplashNavigation.Main
                    }
                }

                !helperUseCase.getPreferenceHelper().isLoggedIn() -> {
                    startNavigation.value = SplashNavigation.Login
                }
            }
        }
    }
}