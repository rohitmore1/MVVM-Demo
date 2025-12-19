package com.demo.mvvm.ui.di.modules

import android.app.Application
import android.content.Context
import com.demo.data.database.repository.LocalDataSource
import com.demo.data.database.repository.LocalDataSourceImpl
import com.demo.data.feature.restapiService.dataStore.RemoteRestAPIDataStore
import com.demo.data.feature.restapiService.dataStore.RestAPIDataStore
import com.demo.data.remote.Remote
import com.demo.data.remote.impl.RemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCloud(@ApplicationContext context: Context): Remote {
        return RemoteImpl(context)
    }


    @Singleton
    @Provides
    fun provideRestAPIDataStore(@ApplicationContext context: Context): RestAPIDataStore {
        return RemoteRestAPIDataStore(context)
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }

}

/*
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCloud(
        @ApplicationContext context: Context
    ): Cloud = CloudImpl(context)

    @Provides
    @Singleton
    fun provideRestAPIDataStore(
        @ApplicationContext context: Context
    ): RestAPIDataStore = CloudRestAPIDataStore(context)
}*/
