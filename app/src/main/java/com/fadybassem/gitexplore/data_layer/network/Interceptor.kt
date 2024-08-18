package com.fadybassem.gitexplore.data_layer.network

import com.fadybassem.gitexplore.data_layer.local.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class Interceptor @Inject constructor(
    private val preferenceHelper: PreferenceHelper,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {

            val request = chain.request().newBuilder().header("Accept", "*/*")
                .header("Accept-Encoding", "gzip,defla,br").header("Connection", "close")
                .header("lang", preferenceHelper.getSelectedLanguage().lang)
                //.header("Content-Type", "application/json")
                .header("Accept-Language", preferenceHelper.getSelectedLanguage().lang)

            return chain.proceed(request.build())
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}