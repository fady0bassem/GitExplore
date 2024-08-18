package com.fadybassem.gitexplore.data_layer.models.github

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)