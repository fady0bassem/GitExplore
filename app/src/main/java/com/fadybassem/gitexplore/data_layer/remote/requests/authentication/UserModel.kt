package com.fadybassem.gitexplore.data_layer.remote.requests.authentication

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserModel(
    @SerializedName("uid") var uid: String? = null,
//    @get:Exclude @SerializedName("uid") var uid: String? = null,
//    @SerializedName("firstName") var firstName: String? = null,
//    @SerializedName("lastName") var lastName: String? = null,
//    @SerializedName("email") var email: String? = null,
//    @SerializedName("phone") var phone: String? = null,
//    @get:Exclude @SerializedName("password") var password: String? = null,
//    @get:Exclude @SerializedName("confirmPassword") var confirmPassword: String? = null,
//    @SerializedName("dateOfBirth") var dateOfBirth: String? = null,
//    @SerializedName("gender") var gender: String? = null,
//    @SerializedName("displayName") var displayName: String? = null,
//    @SerializedName("userType") var userType: String? = null,
//    @SerializedName("appVersion") var appVersion: String? = null,
//    @SerializedName("platform") var platform: String? = null,
) : Parcelable