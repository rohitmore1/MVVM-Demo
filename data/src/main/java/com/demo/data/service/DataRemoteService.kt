package com.demo.data.service

import android.content.Context
import com.demo.api.service.APIService
import com.demo.api.service.APIServiceImpl
import com.demo.data.interceptor.CustomDataInterceptor
import com.demo.data.interceptor.CustomInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class DataRemoteService {

    companion object Companion {
        private var sDataRemoteService: DataRemoteService? = null
        private var apiServiceImpl: APIServiceImpl? = null
        private var client: OkHttpClient? = null
        private var customInterceptor: CustomInterceptor? = null

        fun getInstance(context: Context?): DataRemoteService? {
            if (sDataRemoteService == null) {
                synchronized(APIService::class.java) {
                    if (sDataRemoteService == null) {
                        sDataRemoteService = DataRemoteService()
                    }
                }
            }
            return sDataRemoteService
        }
    }

    public fun initRestfulService(context: Context) {
        apiServiceImpl = APIServiceImpl.getInstance(context)
        initHttpClient(context = context)
        client?.let { client ->
            APIServiceImpl.setHttpClient(client)
            APIServiceImpl.initRestAPI()
        }
    }

    private fun initHttpClient(context: Context) {
        if (customInterceptor == null) {
            customInterceptor = getHttpLoggingInterceptor()
        }
        customInterceptor?.let {
            val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .addInterceptor(CustomDataInterceptor.getInstance(context))
                .addInterceptor(customInterceptor!!)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
            client = okHttpClientBuilder.build()
        }
    }

    fun getHttpLoggingInterceptor(): CustomInterceptor? {
        val httpLoggingInterceptor = CustomInterceptor { message ->
        }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

}