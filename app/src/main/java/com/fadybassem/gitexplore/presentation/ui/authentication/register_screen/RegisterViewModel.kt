package com.fadybassem.gitexplore.presentation.ui.authentication.register_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fadybassem.gitexplore.R
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
class RegisterViewModel @Inject constructor(
    private val helperUseCase: HelperUseCase,
    private val authenticationUseCase: AuthenticationUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), DefaultLifecycleObserver {

    val language = mutableStateOf(helperUseCase.getLanguage())
    val apiStatus = mutableStateOf<Status?>(Status.DEFAULT)

    val showFirebaseErrorDialog = mutableStateOf(false)

    val firstNameTextState = mutableStateOf("")
    val lastNameTextState = mutableStateOf("")
    val emailTextState = mutableStateOf("")
    val passwordTextState = mutableStateOf("")
    val confirmPasswordTextState = mutableStateOf("")

    val passwordVisibility = mutableStateOf(false)
    val confirmPasswordVisibility = mutableStateOf(false)

    val firstNameTextStateError = mutableStateOf(Pair(false, ""))
    val lastNameTextStateError = mutableStateOf(Pair(false, ""))
    val emailTextStateError = mutableStateOf(Pair(false, ""))
    val passwordTextStateError = mutableStateOf(Pair(false, ""))
    val confirmPasswordTextStateError = mutableStateOf(Pair(false, ""))
    val passwordMatchTextStateError = mutableStateOf(Pair(false, ""))

    private val validationResult = arrayListOf<Boolean>()

    val registerResponse: MutableState<Pair<Boolean?, String?>> = mutableStateOf(Pair(null, null))

    init {
        getFirebaseInstanceId()
    }

    fun validateFirstName() {
        helperUseCase.validateEmptyString(value = firstNameTextState.value,
            validationString = resourceProvider.getString(R.string.invalid_first_name),
            onValidate = { isValid, validation ->
                firstNameTextStateError.value = Pair(
                    first = isValid, second = if (isValid) validation else ""
                )
                validationResult.add(isValid)
            })
    }

    fun validateLastName() {
        helperUseCase.validateEmptyString(value = lastNameTextState.value,
            validationString = resourceProvider.getString(R.string.invalid_last_name),
            onValidate = { isValid, validation ->
                lastNameTextStateError.value = Pair(first = isValid, second = validation)
                validationResult.add(isValid)
            })
    }

    fun validateEmail() {
        helperUseCase.validateEmail(
            value = emailTextState.value,
            onValidate = { isValid, validation ->
                emailTextStateError.value = Pair(first = isValid, second = validation)
                validationResult.add((isValid))
            })
    }

    fun validatePassword() {
        helperUseCase.validatePassword(
            value = passwordTextState.value,
            onValidate = { isValid, validation ->
                passwordTextStateError.value = Pair(first = isValid, second = validation)
                validationResult.add((isValid))
            })
    }

    fun validateConfirmPassword() {
        helperUseCase.validatePassword(
            value = confirmPasswordTextState.value,
            onValidate = { isValid, validation ->
                confirmPasswordTextStateError.value = Pair(first = isValid, second = validation)
                validationResult.add((isValid))
            })
    }

    fun validatePasswordMatch() {
        helperUseCase.validatePasswordMatch(password = passwordTextState.value,
            confirmPassword = confirmPasswordTextState.value,
            onValidate = { isValid, validation ->
                passwordMatchTextStateError.value = Pair(first = isValid, second = validation)
                validationResult.add((isValid))
            })
    }

    fun signUp() {
        validationResult.clear()

        validateFirstName()
        validateLastName()
        validateEmail()
        validatePassword()
        validateConfirmPassword()
        validatePasswordMatch()

        val isAllTrue: Boolean = validationResult.any { it }

        if (!isAllTrue) {
            val userRequestModel = UserRequestModel(
                firstName = firstNameTextState.value,
                lastName = lastNameTextState.value,
                email = emailTextState.value,
                password = passwordTextState.value,
            )

            register(userRequestModel = userRequestModel)
        }
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

    private fun register(userRequestModel: UserRequestModel) {
        viewModelScope.launch {
            authenticationUseCase.registerWithEmail(userRequestModel).onEach {
                apiStatus.value = it.apiStatus
                if (it.apiStatus == Status.SUCCESS) {
                    registerResponse.value = Pair(true, it.message)
                } else if (it.apiStatus == Status.FAILED || it.apiStatus == Status.ERROR) {
                    registerResponse.value = Pair(false, it.message)
                }
            }.launchIn(viewModelScope)
        }
    }
}