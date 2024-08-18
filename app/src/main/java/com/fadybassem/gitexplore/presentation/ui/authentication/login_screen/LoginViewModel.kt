package com.fadybassem.gitexplore.presentation.ui.authentication.login_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.fadybassem.gitexplore.usecase.authentication.AuthenticationUseCase
import com.fadybassem.gitexplore.usecase.helper.HelperUseCase
import com.fadybassem.gitexplore.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val showFirebaseErrorDialog = mutableStateOf(false)

    val version = helperUseCase.getVersionString()

    val emailTextState = mutableStateOf("")
    val passwordTextState = mutableStateOf("")

    val passwordVisibility = mutableStateOf(false)

    val emailTextStateError = mutableStateOf(Pair(false, ""))
    val passwordTextStateError = mutableStateOf(Pair(false, ""))

    val navigateToMainScreen: MutableState<Boolean?> = mutableStateOf(null)
    val showErrorDialog: MutableState<Pair<Boolean?, String?>> = mutableStateOf(Pair(null, null))

    init {
        getFirebaseInstanceId()
    }

    private fun getFirebaseInstanceId() {
        viewModelScope.launch {
            authenticationUseCase.getFirebaseInstanceId().onEach {
                when (it.apiStatus) {
                    Status.SUCCESS -> {
                        showFirebaseErrorDialog.value = false
                    }

                    Status.FAILED, Status.ERROR -> {
                        showFirebaseErrorDialog.value = true
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

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
        if (validateLogin()) {
            loginRequest()
        }
    }

    private fun validateLogin(): Boolean {
        onValidateEmail()
        validatePassword()
        return !(emailTextStateError.value.first || passwordTextStateError.value.first)
    }

    private fun loginRequest() {
        viewModelScope.launch {
            val userRequestModel = UserRequestModel(
                email = emailTextState.value.trim { it <= ' ' },
                password = passwordTextState.value.trim { it <= ' ' },
            )

            login(userRequestModel = userRequestModel)
        }
    }

    private fun login(userRequestModel: UserRequestModel) {
        viewModelScope.launch {
            authenticationUseCase.loginWithEmail(userRequestModel).onEach { user ->
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