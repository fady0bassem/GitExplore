package com.fadybassem.gitexplore.usecase.helper

import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.utils.Language

interface HelperUseCase {
    fun getPreferenceHelper(): PreferenceHelper

    fun getLanguage(): Language

    fun getFirebaseInstanceId(): String?

    fun getUser(): User?

    fun validateEmptyString(
        value: String?, validationString: String? = null, onValidate: (Boolean, String) -> Unit,
    )

    fun validateEmail(value: String?, onValidate: (Boolean, String) -> Unit)

    fun validatePhone(value: String?, onValidate: (Boolean, String) -> Unit)

    fun validatePassword(value: String?, onValidate: (isValid: Boolean, validation: String) -> Unit)

    fun validatePasswordMatch(
        password: String?,
        confirmPassword: String?,
        onValidate: (isValid: Boolean, validation: String) -> Unit,
    )

    fun getVersionString(): String
}