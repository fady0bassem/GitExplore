package com.fadybassem.gitexplore.usecase.authentication

import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.repository.authentication.AuthenticationRepository
import com.fadybassem.gitexplore.utils.NAVIGATE
import com.fadybassem.gitexplore.utils.Status
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class AuthenticationUseCaseImpl(
    private val repository: AuthenticationRepository,
    private val preferenceHelper: PreferenceHelper,
    private val resourceProvider: ResourceProvider,
    private val networkManager: NetworkManager,
) : AuthenticationUseCase {

    override fun validateFirebaseInstanceId(onValidate: (Boolean) -> Unit) {
        repository.validateFirebaseInstanceId { onValidate.invoke(it) }
    }

    /**
     * validate network connectivity
     * preform register request
     * */
    override suspend fun register(userRequestModel: UserRequestModel): Flow<Resource<String>> =
        flow {
            if (!networkManager.isNetworkConnected()) {
                emit(Resource.Error(message = resourceProvider.getString(R.string.no_internet_connection)))
                return@flow
            }

            emitAll(registerUserToFirebase(userRequestModel))
        }

    /**
     * call repository with user information to perform register request;
     * */
    private fun registerUserToFirebase(userRequestModel: UserRequestModel) = flow {
        repository.register(userRequestModel).onEach { emit(it) }.collect()
    }


    /**
     * validate network connectivity
     * preform login request
     * */
    override suspend fun login(userRequestModel: UserRequestModel): Flow<Resource<User>> = flow {
        if (!networkManager.isNetworkConnected()) {
            emit(Resource.Error(message = resourceProvider.getString(R.string.no_internet_connection)))
            return@flow
        }

        emitAll(loginUserFromFirebase(userRequestModel))
    }

    /**
     * call repository with email and password to perform login request;
     * update user in users collection
     * save user information in local
     * */
    private fun loginUserFromFirebase(userRequestModel: UserRequestModel) = flow {
        repository.login(userRequestModel).onEach {
            if (it.apiStatus == Status.SUCCESS) {
                // check if user is active
                if (it.data?.active == false) {
                    it.apiStatus = Status.FAILED
                    it.message = resourceProvider.getString(R.string.login_user_active_message)
                } else {
                    // save user date
                    it.data?.let { user -> saveUser(user) }
                }

                // check for first login
                if (it.data?.firstLogin == true) {
                    it.payload = arrayOf(Pair(NAVIGATE, false))
                }
            }
            emit(it)
        }.collect()
    }

    /**
     * set logged in value in local storage
     * save user in local storage
     * */
    private fun saveUser(user: User) {
        preferenceHelper.clearSavedData()
        preferenceHelper.setLoggedIn(true)
        preferenceHelper.setUser(user)
    }

    /**
     * validate network connectivity
     * preform forgot password request
     * */
    override suspend fun forgotPassword(email: String): Flow<Resource<String>> = flow {
        if (!networkManager.isNetworkConnected()) {
            emit(Resource.Error(message = resourceProvider.getString(R.string.no_internet_connection)))
            return@flow
        }

        emitAll(forgotPasswordToFirebase(email))
    }

    /**
     * call repository with email to perform forgot password request;
     * */
    private fun forgotPasswordToFirebase(email: String) = flow {
        repository.forgotPassword(email).onEach { emit(it) }.collect()
    }

    /**
     * validate network connectivity
     * preform complete profile request
     * */
    override suspend fun completeProfile(
        uid: String,
        profilePicture: ByteArray?,
        about: String?,
    ): Flow<Resource<User>> = flow {
        if (!networkManager.isNetworkConnected()) {
            emit(Resource.Error(message = resourceProvider.getString(R.string.no_internet_connection)))
            return@flow
        }

        emitAll(
            completeProfileFromFirebase(
                userID = uid, profilePicture = profilePicture, about = about
            )
        )
    }

    /**
     * call repository with uid, profilePicture and about to perform complete profile request;
     * update user in users collection
     * save user information in local
     * */
    private fun completeProfileFromFirebase(
        userID: String,
        profilePicture: ByteArray?,
        about: String?,
    ) = flow {
        repository.completeProfile(uid = userID, profilePicture = profilePicture, about = about)
            .onEach {
                if (it.apiStatus == Status.SUCCESS) {
                    // save user date
                    it.data?.let { user -> saveUser(user) }
                }
                emit(it)
            }.collect()
    }

}