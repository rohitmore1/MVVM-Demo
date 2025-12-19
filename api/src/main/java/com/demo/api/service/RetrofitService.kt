package com.demo.api.service


import com.demo.api.constant.ApiConstant
import com.demo.api.model.PostsResponse
import com.demo.api.model.PostsResponseResult
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("posts")
    suspend fun getPosts(
        @Query(ApiConstant.USER_ID) userID: String?
    ): List<PostsResponse>?

}