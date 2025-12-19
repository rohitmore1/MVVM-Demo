package com.demo.domain.feature.posts.useCase

import com.demo.domain.common.BaseUseCase
import com.demo.domain.common.Resource
import com.demo.domain.feature.posts.repository.PostsRepository
import com.demo.domain.model.User
import com.demo.domain.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLoggedInUserFromDatabaseUseCase @Inject constructor(
    private val repository: PostsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<User, Nothing>() {

    override suspend fun buildRequest(params: Nothing?): Flow<Resource<User?>>? {
        return try {
            return repository.getLoggedInUserFromDatabase()?.flowOn(dispatcher)
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }

}