package com.fadybassem.gitexplore.usecase.authentication

import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationUseCase {

    fun validateFirebaseInstanceId(onValidate: (Boolean) -> Unit)

    /**
     * validate network connectivity
     * preform register request
     * */
    suspend fun register(userRequestModel: UserRequestModel): Flow<Resource<String>>

    /**
     * validate network connectivity
     * preform login request
     * */
    suspend fun login(userRequestModel: UserRequestModel): Flow<Resource<User>>

    /**
     * validate network connectivity
     * preform forgot password request
     * */
    suspend fun forgotPassword(email: String): Flow<Resource<String>>

    /**
     * validate network connectivity
     * preform complete profile request
     * */
    suspend fun completeProfile(
        uid: String,
        profilePicture: ByteArray?,
        about: String?,
    ): Flow<Resource<User>>
}