package com.demo.data.feature.restapiService.dataStore

import android.content.Context
import com.demo.data.database.repository.LocalDataSource
import com.demo.data.remote.Remote
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestAPIDataStoreFactory @Inject constructor(
    private val mRemote: Remote,
    private val localDataSource: LocalDataSource,
    @ApplicationContext val context: Context
) {

    private var mRemoteRestAPIDataStore: RemoteRestAPIDataStore? = null

    fun createCloudDataStore(): RemoteRestAPIDataStore? {
        if (mRemoteRestAPIDataStore == null) {
            mRemoteRestAPIDataStore = RemoteRestAPIDataStore(context)
        }
        return mRemoteRestAPIDataStore
    }

}