package com.fadybassem.gitexplore.presentation.ui.authentication.login_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.usecase.authentication.AuthenticationUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val version = helperUseCase.getVersionString()

    val emailTextState = mutableStateOf("")
    val passwordTextState = mutableStateOf("")

    val passwordVisibility = mutableStateOf(false)

    val emailTextStateError = mutableStateOf(Pair(false, ""))
    val passwordTextStateError = mutableStateOf(Pair(false, ""))

    val firebaseError: MutableState<Pair<Boolean?, String?>> = mutableStateOf(Pair(false, ""))
    val navigateToCompleteProfileScreen: MutableState<Boolean?> = mutableStateOf(null)
    val navigateToMainScreen: MutableState<Boolean?> = mutableStateOf(null)
    val showErrorDialog: MutableState<Pair<Boolean?, String?>> = mutableStateOf(Pair(null, null))

    fun onValidateEmail() {
        helperUseCase.validateEmail(
            value = emailTextState.value,
            onValidate = { isValid, validation ->
                emailTextStateError.value = Pair(first = isValid, second = validation)
            })
    }

    fun validatePassword() {
        helperUseCase.validatePassword(
            value = passwordTextState.value,
            onValidate = { isValid, validation ->
                passwordTextStateError.value = Pair(first = isValid, second = validation)
            })
    }

    fun loginClick() {

    }
}