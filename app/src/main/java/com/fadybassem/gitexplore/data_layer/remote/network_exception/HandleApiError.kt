package com.fadybassem.gitexplore.data_layer.remote.network_exception

import android.content.Context
import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HandleApiError @Inject constructor(
    @ApplicationContext private val context: Context,
    private val resourceProvider: ResourceProvider,
    private val preferenceHelper: PreferenceHelper,
    private val networkManager: NetworkManager,
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
                Pair(false, "")
            }

            403 -> {
                Pair(false, resourceProvider.getString(R.string.api_error_not_allowed))

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