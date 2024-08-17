package com.fadybassem.gitexplore.data_layer.remote

import com.fadybassem.gitexplore.utils.Status

sealed class Resource<T>(
    val data: T? = null,
    var apiStatus: Status,
    var message: String? = "",
    var code: Int? = null,
    vararg var payload: Pair<String, Any?> = emptyArray(),
) {
    class Loading<T> : Resource<T>(apiStatus = Status.LOADING)

    class Success<T>(
        message: String? = null,
        data: T? = null,
        vararg payload: Pair<String, Any?> = emptyArray(),
    ) : Resource<T>(message = message, data = data, apiStatus = Status.SUCCESS, payload = payload)

    class Error<T>(
        message: String?, code: Int? = null, vararg payload: Pair<String, Any?> = emptyArray(),
    ) : Resource<T>(apiStatus = Status.ERROR, message = message, code = code, payload = payload)

    class Failed<T>(
        message: String? = null,
        data: T? = null,
        code: Int? = null,
        vararg payload: Pair<String, Any?> = emptyArray(),
    ) : Resource<T>(
        message = message, data = data, apiStatus = Status.FAILED, code = code, payload = payload
    )
}