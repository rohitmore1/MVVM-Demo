package com.demo.api.service

import android.content.Context
import com.demo.api.constant.ApiConstant
import com.demo.api.converter.APIResponseConverterFactory
import com.demo.api.model.PostsParameters
import com.demo.api.model.PostsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class APIServiceImpl : APIService {

    companion object {
        private var apiServiceImpl: APIServiceImpl? = null
        private var mRetrofitService: RetrofitService? = null
        private var mClient: OkHttpClient? = null

        fun getInstance(context: Context?): APIServiceImpl? {
            if (apiServiceImpl == null) {
                synchronized(APIServiceImpl::class.java) {
                    if (apiServiceImpl == null) {
                        apiServiceImpl = APIServiceImpl()
                    }
                }
            }
            return apiServiceImpl
        }

        fun setHttpClient(client: OkHttpClient) {
            mClient = client
        }

        fun initRestAPI() {
            var baseUrl = ApiConstant.PRODUCTION_BASE_URL
            mClient?.let { client ->
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(APIResponseConverterFactory())
                    .client(client)
                    .build()
                mRetrofitService = retrofit.create(RetrofitService::class.java)
            }
        }

    }

    override suspend fun getPosts(requestParameters: PostsParameters): List<PostsResponse>? {
        // return mRestAPIService?.getPosts(userID = requestParameters.userID)
        return mRetrofitService?.getPosts(userID = "1")
    }

}