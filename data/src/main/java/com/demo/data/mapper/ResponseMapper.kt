package com.demo.data.mapper

import com.demo.api.model.PostsParameters
import com.demo.api.model.PostsResponse
import com.demo.domain.model.PostItem
import com.demo.domain.model.User


class ResponseMapper {

    companion object Companion {

        fun transformAuthParameters(user: User?): PostsParameters? {
            return user?.let {
                PostsParameters().apply {
                    userID = it.email
                }
            }
        }

         fun transform(
            posts: List<PostsResponse>?
        ): List<PostItem>? {
            return posts?.map { post ->
                PostItem(
                    userId = post.userId,
                    id = post.id,
                    title = post.title,
                    body = post.body
                )
            }
        }
    }

}