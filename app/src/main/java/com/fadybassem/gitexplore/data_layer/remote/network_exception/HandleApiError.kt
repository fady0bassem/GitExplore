package com.fadybassem.gitexplore.data_layer.remote.network_exception

import android.content.Context
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.responses.ErrorResponse
import com.fadybassem.gitexplore.presentation.navigation.startBaseNavigation
import com.fadybassem.gitexplore.presentation.screens.authentication.AuthenticationActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HandleApiError @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resourceProvider: ResourceProvider,
    private val preferenceHelper: PreferenceHelper,
) {

    suspend fun handleApiErrors(
        error: HttpException,
        isWrongCredentials: Boolean = false,
        shouldCheckOnErrorBody: Boolean = false,
    ): Pair<Boolean, String> {
        return when (error.code()) {
            400 -> {
                Pair(false, resourceProvider.getString(R.string.generic_error))
            }

            401 -> {
                preferenceHelper.clearSavedData()
                context.startBaseNavigation(AuthenticationActivity::class.java, null)
                Pair(false, resourceProvider.getString(R.string.generic_error))
            }

            403 -> {
                if (error.response()?.errorBody() != null) {
                    try {
                        val networkError =
                            NetworkException<ErrorResponse>().extractErrorBody(exception = error)/*val gson = Gson()
                        val type = object : TypeToken<ErrorResponse>() {}.type
                        val apiError: ErrorResponse = gson.fromJson(networkError, type)*/

                        if (shouldCheckOnErrorBody) {
                            try {
                                if (networkError.length > 1) {
                                    when (networkError) {

                                        else -> {
                                            return Pair(
                                                first = false,
                                                second = resourceProvider.getString(R.string.generic_error)
                                            )
                                        }
                                    }
                                }
                            } catch (exception: Exception) {
                                exception.printStackTrace()
                                return Pair(
                                    false, resourceProvider.getString(R.string.generic_error)
                                )
                            }
                        } else {
                            if (networkError.length > 1) {
                                //Pair(false, apiError.message)
                                Pair(
                                    false, resourceProvider.getString(R.string.generic_error)
                                )
                            } else Pair(
                                false, resourceProvider.getString(R.string.api_error_not_allowed)
                            )
                        }

                        Pair(false, resourceProvider.getString(R.string.generic_error))
                    } catch (e: Exception) {
                        if (isWrongCredentials) Pair(
                            false, resourceProvider.getString(R.string.generic_error)
                        )
                        else Pair(false, resourceProvider.getString(R.string.api_error_not_allowed))
                    }
                } else {
                    if (isWrongCredentials) Pair(
                        false, resourceProvider.getString(R.string.generic_error)
                    )
                    else Pair(false, resourceProvider.getString(R.string.api_error_not_allowed))
                }

            }

            404 -> {
                Pair(false, resourceProvider.getString(R.string.not_found))
            }

            420 -> {
                Pair(false, resourceProvider.getString(R.string.api_error_not_allowed))
            }

            422 -> {
                Pair(false, resourceProvider.getString(R.string.generic_error))
            }

            500 -> {
                Pair(false, resourceProvider.getString(R.string.generic_error))
            }


            502 -> {
                Pair(false, resourceProvider.getString(R.string.generic_error))
            }

            else -> {
                Pair(false, resourceProvider.getString(R.string.generic_error))
            }
        }
    }
}