package com.demo.domain.feature.domainService.repository

import com.demo.domain.common.Resource
import com.demo.domain.model.ApiServiceParameters
import kotlinx.coroutines.flow.Flow

interface DomainAPIRepository {

    suspend fun initializeRestApiService(apiServiceParameters: ApiServiceParameters?): Flow<Resource<Boolean?>>?

}