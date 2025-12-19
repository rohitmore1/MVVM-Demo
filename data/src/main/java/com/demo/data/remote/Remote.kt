package com.demo.data.remote

import com.demo.api.model.PostsParameters
import com.demo.domain.model.PostItem

interface Remote {

    suspend fun getPosts(requestParameters: PostsParameters): List<PostItem>?

}