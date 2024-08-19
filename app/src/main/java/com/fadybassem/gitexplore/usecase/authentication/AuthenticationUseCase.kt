package com.fadybassem.gitexplore.usecase.authentication

import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationUseCase {

    suspend fun getFirebaseInstanceId(): Flow<Resource<String>>
    suspend fun loginWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>>
    suspend fun registerWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>>
    suspend fun forgotPassword(userRequestModel: UserRequestModel): Flow<Resource<String>>
    suspend fun logout(): Flow<Resource<Unit>>
}