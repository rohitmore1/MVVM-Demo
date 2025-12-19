package com.demo.data.feature.auth.dataStore

import com.demo.data.database.repository.LocalDataSource
import com.demo.data.remote.Remote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStoreFactory @Inject constructor(
    private val mRemote: Remote,
    private val localDataSource: LocalDataSource
) {
    private var dataStore: RemoteAuthDataStore? = null

    fun createRemoteDataStore(): RemoteAuthDataStore? {
        if (dataStore == null) {
            dataStore = RemoteAuthDataStore(localDataSource, mRemote)
        }
        return dataStore
    }

}