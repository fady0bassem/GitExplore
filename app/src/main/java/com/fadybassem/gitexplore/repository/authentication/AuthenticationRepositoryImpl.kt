package com.fadybassem.gitexplore.repository.authentication

import com.fadybassem.gitexplore.data_layer.dto.authentication.AuthenticationDTOMapper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.network_exception.firebase.HandleFireBaseError
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl(
    private val resourceProvider: ResourceProvider,
    private val handleErrorResponse: HandleFireBaseError,
    private val firebaseMessaging: FirebaseMessaging,
    private val firebaseAuth: FirebaseAuth,
    private val domainMapper: AuthenticationDTOMapper,
) : AuthenticationRepository {

    override fun loginWithEmail(email: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user!!
            val data = domainMapper.mapFromFirebaseUser(firebaseUser)

            emit(Resource.Success(data = data))
        } catch (exception: Exception) {
            emit(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    override fun registerWithEmail(email: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user!!
            val data = domainMapper.mapFromFirebaseUser(firebaseUser)

            emit(Resource.Success(data = data))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage, code = 401))
        }
    }

    override fun loginWithGoogle(token: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(token, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user!!
            val data = domainMapper.mapFromFirebaseUser(firebaseUser)

            emit(Resource.Success(data = data))
        } catch (exception: Exception) {
            emit(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    override fun loginWithFacebook(token: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val credential = com.google.firebase.auth.FacebookAuthProvider.getCredential(token)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user!!
            val data = domainMapper.mapFromFirebaseUser(firebaseUser)

            emit(Resource.Success(data = data))
        } catch (exception: Exception) {
            emit(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    override fun getFirebaseInstanceId(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val token = firebaseMessaging.token.await()
            if (token.isNotEmpty()) {
                emit(Resource.Success(data = token))
            } else {
                emit(Resource.Failed(data = null))
            }

        } catch (exception: Exception) {
            emit(Resource.Failed<String>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

}