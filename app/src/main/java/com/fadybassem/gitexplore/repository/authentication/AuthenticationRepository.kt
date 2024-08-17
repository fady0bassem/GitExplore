package com.fadybassem.gitexplore.repository.authentication

import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun validateFirebaseInstanceId(onValidate: (Boolean) -> Unit)

    /**
     * create user with email and password in firebase authentication
     * */
    fun register(userRequestModel: UserRequestModel): Flow<Resource<String>>

    /**
     * login with email and password in firebase authentication
     * if login success, update user login status
     * */
    fun login(userRequestModel: UserRequestModel): Flow<Resource<User>>

    /**
     * forgot password
     * */
    suspend fun forgotPassword(email: String): Flow<Resource<String>>

    /**
     * preform complete profile request
     * */
    suspend fun completeProfile(
        uid: String,
        profilePicture: ByteArray?,
        about: String?,
    ): Flow<Resource<User>>
}