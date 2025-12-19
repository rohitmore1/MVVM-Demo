package com.demo.data.feature.auth.dataStore

import com.demo.api.model.PostsParameters
import com.demo.data.database.repository.LocalDataSource
import com.demo.data.mapper.ResponseMapper
import com.demo.data.remote.Remote
import com.demo.domain.common.Resource
import com.demo.domain.model.PostItem
import com.demo.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteAuthDataStore @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val mRemote: Remote?
) : AuthDataStore {

    override suspend fun getLoggedInUserFromDatabase(): Flow<Resource<User?>>? {
        return flow {
            val data: User? = localDataSource?.getLoggedInUserFromDatabase()

            data?.let {
                emit(Resource.Success(data))
            } ?: kotlin.run {
                emit(Resource.Empty)
            }
        }
    }

    override suspend fun getPosts(requestParameters: User): Flow<Resource<List<PostItem>?>>? {
        return flow {
            try {
                var postsParameters: PostsParameters? =
                    ResponseMapper.transformAuthParameters(requestParameters)
                postsParameters?.let {
                    val data = mRemote?.getPosts(requestParameters = postsParameters)
                    data?.let {
                        emit(Resource.Success(data))
                    } ?: kotlin.run {
                        emit(Resource.Empty)
                    }
                } ?: kotlin.run {
                    emit(Resource.Empty)
                }
            } catch (ex: Exception) {
                emit(Resource.Error(ex))
            }
        }
    }

}