package com.demo.domain.common

import androidx.annotation.Nullable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<Model, Params> {

    abstract suspend fun buildRequest(@Nullable params: Params?): Flow<Resource<Model?>>?

    suspend fun execute(@Nullable params: Params?): Flow<Resource<Model?>>? {
        return try {
            buildRequest(params)
        } catch (exception: Exception) {
            flow { emit(Resource.Error(exception)) }
        }
    }
}