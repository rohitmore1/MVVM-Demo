package com.demo.domain.feature.domainService.useCase

import com.demo.domain.common.BaseUseCase
import com.demo.domain.common.Resource
import com.demo.domain.feature.domainService.repository.DomainAPIRepository
import com.demo.domain.model.ApiServiceParameters
import com.demo.domain.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InitializeApiServiceUseCase @Inject constructor(
    private val repository: DomainAPIRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<Boolean, ApiServiceParameters>() {

    override suspend fun buildRequest(params: ApiServiceParameters?): Flow<Resource<Boolean?>>? {
        return try {
            params?.let {
                return repository.initializeRestApiService(apiServiceParameters = params)
                    ?.flowOn(dispatcher)
            } ?: kotlin.run {
                flow { emit(Resource.Empty) }
            }
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }

}