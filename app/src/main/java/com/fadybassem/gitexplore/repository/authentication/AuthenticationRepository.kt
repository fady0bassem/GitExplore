package com.fadybassem.gitexplore.repository.authentication

import com.facebook.AccessToken
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun loginWithEmail(email: String, password: String): Flow<Resource<User>>
    fun registerWithEmail(email: String, password: String): Flow<Resource<User>>
    fun loginWithGoogle(token: String): Flow<Resource<User>>
    fun loginWithFacebook(token: String): Flow<Resource<User>>
    fun getFirebaseInstanceId(): Flow<Resource<String>>
}