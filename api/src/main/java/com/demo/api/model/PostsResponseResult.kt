package com.demo.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponseResult(
    val posts: List<PostsResponse>? = null,
)

@Serializable
data class PostsResponse(
    @SerializedName(value = "userId") val userId: String? = null,
    @SerializedName(value = "id") val id: String? = null,
    @SerializedName(value = "title") val title: String? = null,
    @SerializedName(value = "body") val body: String? = null
)