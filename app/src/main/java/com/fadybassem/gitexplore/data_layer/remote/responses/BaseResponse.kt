package com.fadybassem.gitexplore.data_layer.remote.responses

abstract class BaseResponse {
    val isSuccess: Boolean? = null
    val successMessage: String? = null
    val statusCode: Int? = null
    val dataObject = null
    private val errorList: List<String>? = null

    fun errors(): String {
        val error = StringBuilder()
        errorList?.forEach { e -> error.append("$e\n") }
        return error.toString()
    }
}
