package com.demo.data.feature.auth.dataStore

import com.demo.domain.common.Resource
import com.demo.domain.model.PostItem
import com.demo.domain.model.PostList
import com.demo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {

    suspend fun getLoggedInUserFromDatabase(): Flow<Resource<User?>>?

    suspend fun getPosts(requestParameters: User): Flow<Resource<List<PostItem>?>>?

}