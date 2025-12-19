package com.demo.data.feature.restapiService.dataStore

import com.demo.domain.common.Resource
import com.demo.domain.model.ApiServiceParameters
import kotlinx.coroutines.flow.Flow

interface RestAPIDataStore {

    suspend fun initializeRestApiService(apiServiceParameters: ApiServiceParameters?):
            Flow<Resource<Boolean?>>?

}