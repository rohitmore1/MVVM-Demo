package com.demo.data.feature.restapiService.dataStore

import android.content.Context
import com.demo.data.service.DataRemoteService
import com.demo.domain.common.Resource
import com.demo.domain.model.ApiServiceParameters
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRestAPIDataStore @Inject constructor(@ApplicationContext val context: Context) : RestAPIDataStore {

    override suspend fun initializeRestApiService(apiServiceParameters: ApiServiceParameters?): Flow<Resource<Boolean?>>? {
        return flow {
            try {
                context.let {
                    DataRemoteService.getInstance(context)?.initRestfulService(context)
                }
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Success(false))
            }
        }
    }
}