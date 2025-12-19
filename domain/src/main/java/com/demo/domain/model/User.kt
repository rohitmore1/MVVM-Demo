package com.demo.domain.model

class User {
    var id: String? = null
    var userID: String? = null
    var email: String? = null
    var password: String? = null
}

data class PostList(
    val posts: List<PostItem>? = null,
)

data class PostItem(
    val userId: String? = null,
    val id: String? = null,
    val title: String? = null,
    val body: String? = null
)