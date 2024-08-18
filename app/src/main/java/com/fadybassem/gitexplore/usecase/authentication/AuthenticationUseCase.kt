package com.fadybassem.gitexplore.usecase.authentication

import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import kotlinx.coroutines.flow.Flow

interface AuthenticationUseCase {

    suspend fun loginWithEmail(email: String, password: String): Flow<Resource<User>>
    suspend fun registerWithEmail(email: String, password: String): Flow<Resource<User>>
    suspend fun loginWithGoogle(token: String): Flow<Resource<User>>
    suspend fun loginWithFacebook(token: String): Flow<Resource<User>>
    suspend fun getFirebaseInstanceId(): Flow<Resource<String>>
}