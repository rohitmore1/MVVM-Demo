package com.demo.domain.feature.posts.useCase

import com.demo.domain.common.BaseUseCase
import com.demo.domain.common.Resource
import com.demo.domain.feature.posts.repository.PostsRepository
import com.demo.domain.model.PostItem
import com.demo.domain.model.PostList
import com.demo.domain.model.User
import com.demo.domain.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<List<PostItem>?, User>() {

    override suspend fun buildRequest(params: User?): Flow<Resource<List<PostItem>?>>? {
        return try {
            params?.let {
                return repository.getPosts(requestParameters = params)
                    ?.flowOn(dispatcher)
            } ?: kotlin.run {
                flow {
                    emit(Resource.Empty)
                }
            }
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }

}