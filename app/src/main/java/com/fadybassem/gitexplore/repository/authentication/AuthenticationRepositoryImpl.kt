package com.fadybassem.gitexplore.repository.authentication

import com.fadybassem.gitexplore.data_layer.dto.authentication.AuthenticationDTOMapper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.network_exception.firebase.HandleFireBaseError
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
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

    override fun loginWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.signInWithEmailAndPassword(
                userRequestModel.email ?: "", userRequestModel.password ?: ""
            ).await()

            val firebaseUser = result.user!!

            val data = domainMapper.mapFromFirebaseUser(firebaseUser)

            emit(Resource.Success(data = data))
        } catch (exception: Exception) {
            emit(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    override fun registerWithEmail(userRequestModel: UserRequestModel): Flow<Resource<User>> =
        flow {
            try {
                emit(Resource.Loading())

                val result = firebaseAuth.createUserWithEmailAndPassword(
                    userRequestModel.email ?: "", userRequestModel.password ?: ""
                ).await()

                val firebaseUser = result.user!!

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(userRequestModel.getDisplayName()).build()

                firebaseUser.updateProfile(profileUpdates).await()

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