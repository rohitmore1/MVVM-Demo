package com.demo.data.interceptor

import android.content.Context
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class CustomDataInterceptor private constructor(
    private val context: Context
) : Interceptor {

    companion object {
        private const val HEADER_CONTENT_TYPE_KEY = "Content-Type"
        private const val HEADER_ACCEPT_KEY = "Accept"

        private const val HEADER_CONTENT_TYPE_VALUE = "application/json"
        private const val HEADER_ACCEPT_VALUE = "application/json"

        @Volatile
        private var instance: CustomDataInterceptor? = null

        fun getInstance(context: Context): CustomDataInterceptor =
            instance ?: synchronized(this) {
                instance ?: CustomDataInterceptor(context.applicationContext).also {
                    instance = it
                }
            }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val request = chain.request()

            val headers = Headers.Builder()
                .set(HEADER_ACCEPT_KEY, HEADER_ACCEPT_VALUE)
                .set(HEADER_CONTENT_TYPE_KEY, HEADER_CONTENT_TYPE_VALUE)
                .build()

            val newRequest = request.newBuilder()
                .headers(headers)
                .build()

            chain.proceed(newRequest)
        } catch (e: Throwable) {
            Timber.e(e, "Exception in DefaultInterceptor intercept")
            throw e
        }
    }
}
