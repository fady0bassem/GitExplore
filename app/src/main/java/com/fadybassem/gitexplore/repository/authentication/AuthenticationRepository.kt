package com.fadybassem.gitexplore.repository.authentication

import com.facebook.AccessToken
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun loginWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>>
    fun registerWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>>
    fun loginWithGoogle(token: String): Flow<Resource<User>>
    fun loginWithFacebook(token: String): Flow<Resource<User>>
    fun getFirebaseInstanceId(): Flow<Resource<String>>
}