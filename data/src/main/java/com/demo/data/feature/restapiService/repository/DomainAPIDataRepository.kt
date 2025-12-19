package com.demo.data.feature.restapiService.repository

import com.demo.data.feature.restapiService.dataStore.RemoteRestAPIDataStore
import com.demo.data.feature.restapiService.dataStore.RestAPIDataStoreFactory
import com.demo.domain.common.Resource
import com.demo.domain.feature.domainService.repository.DomainAPIRepository
import com.demo.domain.model.ApiServiceParameters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DomainAPIDataRepository @Inject constructor(private val restAPIDataStoreFactory: RestAPIDataStoreFactory) :
    DomainAPIRepository {

    override suspend fun initializeRestApiService(apiServiceParameters: ApiServiceParameters?): Flow<Resource<Boolean?>>? {
        val dataStore: RemoteRestAPIDataStore? =
            restAPIDataStoreFactory.createCloudDataStore()
        return dataStore?.initializeRestApiService(apiServiceParameters = apiServiceParameters)
    }

}