package com.fadybassem.gitexplore.usecase.authentication

import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.network.NetworkManager
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.repository.authentication.AuthenticationRepository
import com.fadybassem.gitexplore.utils.checkForNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class AuthenticationUseCaseImpl(
    private val repository: AuthenticationRepository,
    private val preferenceHelper: PreferenceHelper,
    private val resourceProvider: ResourceProvider,
    private val networkManager: NetworkManager,
) : AuthenticationUseCase {


    override suspend fun loginWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>> =
        flow {
            if (checkForNetwork(networkManager, resourceProvider)) return@flow

            repository.loginWithEmail(userRequestModel = userRequestModel).onEach {
                emit(it)
            }.collect()
        }

    override suspend fun registerWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>> =
        flow {
            if (checkForNetwork(networkManager, resourceProvider)) return@flow

            repository.registerWithEmail(userRequestModel = userRequestModel).onEach { emit(it) }.collect()
        }

    override suspend fun loginWithGoogle(token: String): Flow<Resource<User>> {
        return repository.loginWithGoogle(token)
    }

    override suspend fun loginWithFacebook(token: String): Flow<Resource<User>> {
        return repository.loginWithFacebook(token)
    }

    override suspend fun getFirebaseInstanceId(): Flow<Resource<String>> = flow {
        repository.getFirebaseInstanceId().collect { resource ->
            if (resource is Resource.Success) {
                //localStorageManager.saveToken(resource.data!!) // Save token to local storage
                //preferenceHelper.setFirebaseInstanceID(id = resource.data)
            }
            emit(resource)
        }
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
}