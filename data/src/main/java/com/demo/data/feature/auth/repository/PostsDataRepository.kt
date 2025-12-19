package com.demo.data.feature.auth.repository

import com.demo.data.feature.auth.dataStore.AuthDataStoreFactory
import com.demo.data.feature.auth.dataStore.RemoteAuthDataStore
import com.demo.domain.common.Resource
import com.demo.domain.feature.posts.repository.PostsRepository
import com.demo.domain.model.PostItem
import com.demo.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostsDataRepository @Inject constructor(private val dataStoreFactory: AuthDataStoreFactory) :
    PostsRepository {

    override suspend fun getLoggedInUserFromDatabase(): Flow<Resource<User?>>? {
        val remoteAuthDataStore: RemoteAuthDataStore? = dataStoreFactory.createRemoteDataStore()
        return remoteAuthDataStore?.getLoggedInUserFromDatabase()
    }

    override suspend fun getPosts(requestParameters: User): Flow<Resource<List<PostItem>?>>? {
        val remoteAuthDataStore: RemoteAuthDataStore? = dataStoreFactory.createRemoteDataStore()
        return remoteAuthDataStore?.getPosts(requestParameters = requestParameters)
    }

}