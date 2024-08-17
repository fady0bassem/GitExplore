package com.fadybassem.gitexplore.data_layer.remote.network_exception

import retrofit2.HttpException

class NetworkException<T> {
    fun extractErrorBody(exception: HttpException): String{
        return try{
            return exception.response()?.errorBody()?.string().toString()
        } catch (exception: Exception){
            exception.toString()
        }
    }
}