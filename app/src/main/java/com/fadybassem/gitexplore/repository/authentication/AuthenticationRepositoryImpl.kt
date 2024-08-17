package com.fadybassem.gitexplore.repository.authentication

import com.fadybassem.gitexplore.R
import com.fadybassem.gitexplore.data_layer.dto.authentication.AuthenticationDTOMapper
import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import com.fadybassem.gitexplore.data_layer.local.ResourceProvider
import com.fadybassem.gitexplore.data_layer.remote.Resource
import com.fadybassem.gitexplore.data_layer.remote.network_exception.firebase.HandleFireBaseError
import com.fadybassem.gitexplore.data_layer.remote.responses.authenticaion.User
import com.fadybassem.gitexplore.utils.Logger
import com.fadybassem.gitexplore.utils.Status
import com.fadybassem.gitexplore.data_layer.remote.requests.authentication.UserRequestModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl(
    private val resourceProvider: ResourceProvider,
    private val handleErrorResponse: HandleFireBaseError,
    private val preferenceHelper: PreferenceHelper,
    private val firebaseAuth: FirebaseAuth,
    private val domainMapper: AuthenticationDTOMapper,
) : AuthenticationRepository {

    override fun validateFirebaseInstanceId(onValidate: (Boolean) -> Unit) {
        if (preferenceHelper.getFirebaseInstanceID().isNullOrEmpty()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Logger.error("Fetching FCM registration token failed", task.exception)
                    onValidate(false)
                    return@OnCompleteListener
                } else {
                    val token = task.result
                    Logger.info(token)
                    preferenceHelper.setFirebaseInstanceID(token!!)
                    onValidate(true)
                }
            })
        } else {
            onValidate(true)
        }
    }

    /**
     * create user with email and password in firebase authentication
     * */
    override fun register(userRequestModel: UserRequestModel): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            val authResult = firebaseAuth.createUserWithEmailAndPassword(
                userRequestModel.email!!, userRequestModel.password!!
            ).await()

            userRequestModel.uid = authResult.user?.uid

            addUserToDatabase(userRequestModel = userRequestModel).collect {
                emit(Resource.Success(message = it.message))
            }

        } catch (exception: Exception) {
            emit(Resource.Failed<String>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    /**
     * add registered user to 'users' collection
     * */
    private suspend fun addUserToDatabase(userRequestModel: UserRequestModel): Flow<Resource<UserRequestModel>> =
        callbackFlow {
//            database.collection(CollectionNames.USERS.collectionName)
//                .document(userRequestModel.uid!!).set(userRequestModel).addOnSuccessListener {
//                    trySend(
//                        Resource.Success(
//                            message = resourceProvider.getString(R.string.register_success_message),
//                            data = userRequestModel
//                        )
//                    )
//                }.addOnFailureListener {
//                    trySend(Resource.Failed(message = it.message))
//                }
            awaitClose { }
        }

    /**
     * login with email and password in firebase authentication
     * if login success, update user login status
     * */
    override fun login(userRequestModel: UserRequestModel): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val authResult = firebaseAuth.signInWithEmailAndPassword(
                userRequestModel.email!!, userRequestModel.password!!
            ).await()

            userRequestModel.uid = authResult.user?.uid

            val updateResult = updateUserStatusDocument(userRequestModel.uid!!).first()

            if (updateResult.apiStatus == Status.SUCCESS) {
                val getUserResult = getUserFromDatabase(userRequestModel.uid!!).first()
                emit(
                    Resource.Success(
                        message = getUserResult.message, data = getUserResult.data
                    )
                )
            } else {
                emit(Resource.Failed(message = updateResult.message))
            }
        } catch (exception: Exception) {
            emit(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    /**
     * update logged in user in 'users' collection
     * lastLogin: current timestamp
     * platform: android
     * */
    private fun updateUserStatusDocument(uid: String): Flow<Resource<UserRequestModel>> =
        channelFlow {
//            try {
//                database.collection(CollectionNames.USERS.collectionName).document(uid).update(
//                    LAST_LOGIN, Timestamp.now().toDate(), PLATFORM, PLATFORM_ANDROID
//                ).await()
//                send(Resource.Success(message = resourceProvider.getString(R.string.success)))
//            } catch (exception: Exception) {
//                send(Resource.Failed<UserRequestModel>().apply {
//                    message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
//                })
//            }
        }

    /**
     * get logged in user from 'users' collection
     * */
    private fun getUserFromDatabase(uid: String): Flow<Resource<User>> = channelFlow {
        try {
//            val response =
//                database.collection(CollectionNames.USERS.collectionName).document(uid).get()
//                    .await()
//            val mappedResponse = domainMapper.mapUserResponse(response)
//            send(
//                Resource.Success(
//                    message = resourceProvider.getString(R.string.success), data = mappedResponse
//                )
//            )
        } catch (exception: Exception) {
            send(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    /**
     * forgot password
     * */
    override suspend fun forgotPassword(email: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())

            firebaseAuth.sendPasswordResetEmail(email).await()

            emit(Resource.Success(message = resourceProvider.getString(R.string.forgot_password_success)))
        } catch (exception: Exception) {
            emit(Resource.Failed<String>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    /**
     * preform complete profile request
     * add profile image to firebase storage first
     * add about me to user document
     * */
    override suspend fun completeProfile(
        uid: String,
        profilePicture: ByteArray?,
        about: String?,
    ): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            if (profilePicture != null) {
                // upload profile picture first
                uploadProfilePicture(
                    userID = uid, profilePicture = profilePicture
                ).onEach { uploadedProfilePictureResponse ->
                    if (uploadedProfilePictureResponse.apiStatus == Status.SUCCESS) {
                        // get profile picture url
                        val url = uploadedProfilePictureResponse.data

                        // update url and about to user document
                        val updateResult = updateUserProfile(
                            uid = uid, profilePicture = url, about = about
                        ).first()
                        if (updateResult.apiStatus == Status.SUCCESS) {
                            val getUserResult = getUserFromDatabase(uid).first()
                            emit(getUserResult)
                        } else {
                            emit(Resource.Failed(message = updateResult.message))
                        }
                    } else {
                        emit(Resource.Failed(message = uploadedProfilePictureResponse.message))
                    }
                }.first()
            } else {
                // update url and about to user document
                val updateResult = updateUserProfile(uid = uid, about = about).first()
                if (updateResult.apiStatus == Status.SUCCESS) {
                    val getUserResult = getUserFromDatabase(uid).first()
                    emit(getUserResult)
                } else {
                    emit(Resource.Failed(message = updateResult.message))
                }
            }
        } catch (exception: Exception) {
            emit(Resource.Failed<User>().apply {
                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
            })
        }
    }

    /**
     * add profile image to firebase storage
     * return success with url image
     * return fail with error message
     * */
    private suspend fun uploadProfilePicture(
        userID: String?,
        profilePicture: ByteArray,
    ): Flow<Resource<String>> = channelFlow {
//        try {
//            val snapshot = storageReference.root.child(StorageNames.USERS.storageName)
//                .child(userID ?: "${System.currentTimeMillis()}").putBytes(profilePicture).await()
//            val uri = snapshot.storage.downloadUrl.await()
//            send(
//                Resource.Success(
//                    message = resourceProvider.getString(R.string.success), data = uri.toString()
//                )
//            )
//        } catch (exception: Exception) {
//            send(Resource.Failed<String>().apply {
//                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
//            })
//        }
    }

    /**
     * update user info in 'users' collection
     * updatedAt: current timestamp
     * @profilePicture: url
     * @about: string
     * firstLogin: false
     * */
    private fun updateUserProfile(
        uid: String,
        profilePicture: String? = null,
        about: String? = null,
    ): Flow<Resource<UserRequestModel>> = channelFlow {
//        try {
//            database.collection(CollectionNames.USERS.collectionName).document(uid).update(
//                UPDATED_AT,
//                Timestamp.now().toDate(),
//                PROFILE_PICTURE,
//                profilePicture,
//                ABOUT,
//                about,
//                FIRST_LOGIN,
//                false
//            ).await()
//            send(Resource.Success(message = resourceProvider.getString(R.string.success)))
//        } catch (exception: Exception) {
//            send(Resource.Failed<UserRequestModel>().apply {
//                message = handleErrorResponse.handleAuthenticationErrorResponse(exception)
//            })
//        }
    }
}