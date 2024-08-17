package com.fadybassem.gitexplore.data_layer.remote.responses

import com.google.gson.annotations.SerializedName

data class ErrorResponse (

    val statusCode: Int = 0,

    @SerializedName("message")
    val message: String? = null
)