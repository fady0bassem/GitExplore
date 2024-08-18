package com.fadybassem.gitexplore.presentation.ui.authentication.forgot_pssword_screen

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val emailTextState = mutableStateOf("")

    val emailTextStateError = mutableStateOf(Pair(false, ""))

    val forgotPasswordResponse: MutableState<Pair<Boolean?, String?>> =
        mutableStateOf(Pair(null, null))

    fun onValidateEmail() {
        //emailTextStateError.value = emailTextState.value.isEmpty()
        helperUseCase.validateEmail(value = emailTextState.value,
            onValidate = { isValid, validation ->
                emailTextStateError.value = Pair(first = isValid, second = validation)
            })
    }

    fun submitClick() {
        onValidateEmail()

        if (!emailTextStateError.value.first) {
            forgotPassword(emailTextState.value)
        }
    }

    private fun forgotPassword(email: String) {
        viewModelScope.launch {
//            authenticationUseCase.forgotPassword(email).onEach {
//                apiStatus.value = it.apiStatus
//                if (it.apiStatus == Status.SUCCESS) {
//                    forgotPasswordResponse.value = Pair(true, it.message)
//                } else if (it.apiStatus == Status.FAILED || it.apiStatus == Status.ERROR) {
//                    forgotPasswordResponse.value = Pair(false, it.message)
//                }
//            }.launchIn(viewModelScope)
        }
    }
}