package com.fadybassem.gitexplore.data_layer.remote.network_exception.firebase

import android.content.Context
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HandleFireBaseError @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resourceProvider: ResourceProvider,
    private val preferenceHelper: PreferenceHelper,
) {
    fun handleAuthenticationErrorResponse(exception: Exception): String {
        exception.printStackTrace()

        return when (exception) {
            is FirebaseAuthException -> {
                when (exception.errorCode) {
                    "ERROR_INVALID_EMAIL" -> {
                        resourceProvider.getString(R.string.auth_invalid_email)
                    }

                    "ERROR_WRONG_PASSWORD" -> {
                        resourceProvider.getString(R.string.auth_wrong_password)
                    }

                    "ERROR_USER_NOT_FOUND" -> {
                        resourceProvider.getString(R.string.auth_not_found)
                    }

                    "ERROR_USER_DISABLED" -> {
                        resourceProvider.getString(R.string.auth_user_disabled)
                    }

                    "ERROR_TOO_MANY_REQUESTS" -> {
                        resourceProvider.getString(R.string.auth_too_many_requests)
                    }

                    "ERROR_OPERATION_NOT_ALLOWED" -> {
                        resourceProvider.getString(R.string.auth_operation_not_allowed)
                    }

                    "ERROR_EMAIL_ALREADY_IN_USE" -> {
                        resourceProvider.getString(R.string.auth_email_already_exists)
                    }

                    "ERROR_WEAK_PASSWORD" -> {
                        resourceProvider.getString(R.string.auth_weak_password)
                    }

                    "ERROR_INVALID_CREDENTIAL" -> {
                        resourceProvider.getString(R.string.auth_invalid_credential)
                    }

                    else -> {
                        resourceProvider.getString(R.string.generic_error)
                    }
                }
            }

            else -> {
                resourceProvider.getString(R.string.generic_error)
            }
        }
    }
}