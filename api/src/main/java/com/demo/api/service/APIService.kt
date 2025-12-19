package com.demo.api.service

import com.demo.api.model.PostsParameters
import com.demo.api.model.PostsResponse


interface APIService {

    companion object {
        fun getInstance(): APIService? {
            return APIServiceImpl()
        }
    }

    suspend fun getPosts(requestParameters: PostsParameters): List<PostsResponse>?

}