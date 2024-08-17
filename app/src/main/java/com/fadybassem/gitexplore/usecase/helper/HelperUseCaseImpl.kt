package com.fadybassem.gitexplore.usecase.helper

import com.fadybassem.gitexplore.BuildConfig
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.AppConfiguration.Companion.PASSWORD_LENGTH
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.utils.isEmailValid
import com.fadybassem.gitexplore.utils.isPhoneNumberValid

class HelperUseCaseImpl(
    private val preferenceHelper: PreferenceHelper,
    private val resourceProvider: ResourceProvider,
) : HelperUseCase {

    override fun getPreferenceHelper() = preferenceHelper

    override fun getLanguage(): Language = preferenceHelper.getSelectedLanguage()

    override fun getFirebaseInstanceId(): String? = preferenceHelper.getFirebaseInstanceID()

    override fun getUser(): User? = preferenceHelper.getUser()

    override fun validateEmptyString(
        value: String?,
        validationString: String?,
        onValidate: (isValid: Boolean, validation: String) -> Unit,
    ) {
        if (value?.isEmpty() == true) {
            onValidate.invoke(
                true, validationString ?: resourceProvider.getString(R.string.empty_input)
            )
        } else {
            onValidate.invoke(false, "")
        }
    }

    override fun validateEmail(
        value: String?, onValidate: (isValid: Boolean, validation: String) -> Unit,
    ) {
        if (value?.isNotEmpty() == true) {
            if (isEmailValid(value.lowercase().trim())) {
                onValidate.invoke(false, "")
            } else {
                onValidate.invoke(true, resourceProvider.getString(R.string.invalid_email))
            }
        } else {
            onValidate.invoke(true, resourceProvider.getString(R.string.empty_input))
        }
    }

    override fun validatePhone(
        value: String?, onValidate: (isValid: Boolean, validation: String) -> Unit,
    ) {
        if (value?.isEmpty() == true) {
            onValidate.invoke(true, resourceProvider.getString(R.string.empty_input))
        } else {
            if (isPhoneNumberValid(phoneNumber = value?.lowercase()?.trim() ?: "")) {
                onValidate.invoke(false, "")
            } else {
                onValidate.invoke(true, resourceProvider.getString(R.string.empty_mobile_number))
            }
        }
    }

    override fun validatePassword(
        value: String?,
        onValidate: (isValid: Boolean, validation: String) -> Unit,
    ) {
        if (value?.isEmpty() == true) {
            onValidate.invoke(true, resourceProvider.getString(R.string.password_required))
        } else {
            if (value?.length!! < PASSWORD_LENGTH) {
                onValidate.invoke(
                    true, resourceProvider.getString(R.string.invalid_password_length)
                )
            } else {
                onValidate.invoke(false, "")
            }
        }
    }

    override fun validatePasswordMatch(
        password: String?,
        confirmPassword: String?,
        onValidate: (isValid: Boolean, validation: String) -> Unit,
    ) {
        if (password?.isNotEmpty() == true && confirmPassword?.isNotEmpty() == true) {
            if (password == confirmPassword) {
                onValidate.invoke(false, "")
            } else {
                onValidate.invoke(true, resourceProvider.getString(R.string.unmatched_password))
            }
        }
    }

    override fun getVersionString(): String =
        "${resourceProvider.getString(R.string.version)} ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
}