package com.fadybassem.gitexplore.repository.authentication

import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun loginWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>>
    fun registerWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>>
    fun getFirebaseInstanceId(): Flow<Resource<String>>
}