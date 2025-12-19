package com.demo.data.remote.impl

import android.content.Context
import com.demo.api.model.PostsParameters
import com.demo.api.service.APIService
import com.demo.data.mapper.ResponseMapper
import com.demo.data.remote.Remote
import com.demo.domain.model.PostItem
import javax.inject.Inject

class RemoteImpl @Inject constructor(context: Context) : Remote {


    private var service: APIService? = null

    init {
        service = APIService.getInstance()
    }

    override suspend fun getPosts(requestParameters: PostsParameters): List<PostItem>? {
        val data = service?.getPosts(requestParameters = requestParameters)
        return ResponseMapper.transform(data)
    }

}